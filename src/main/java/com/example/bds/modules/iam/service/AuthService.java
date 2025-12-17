package com.example.bds.modules.iam.service;

import com.example.bds.modules.iam.dto.auth.*;
import com.example.bds.modules.iam.entity.PasswordResetToken;
import com.example.bds.modules.iam.entity.User;
import com.example.bds.modules.iam.entity.UserStatus;
import com.example.bds.modules.iam.repository.PasswordResetTokenRepository;
import com.example.bds.modules.iam.repository.UserRepository;
import com.example.bds.modules.iam.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender mailSender;

    /**
     * Đăng ký user mới
     */
    @Transactional
    public LoginResponseDTO register(RegisterRequestDTO registerDTO) {
        // Kiểm tra username đã tồn tại
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }

        // Kiểm tra email đã tồn tại
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        // Kiểm tra phone đã tồn tại
        if (userRepository.existsByPhone(registerDTO.getPhone())) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }

        // Tạo user mới
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setFullName(registerDTO.getFullName());
        user.setPhone(registerDTO.getPhone());
        user.setAddress(registerDTO.getAddress());
        user.setStatus(UserStatus.ACTIVE);

        user = userRepository.save(user);

        // Generate JWT token
        String token = jwtUtils.generateToken(user.getUsername());

        return new LoginResponseDTO(token, user.getId(), user.getUsername(), user.getEmail(), user.getFullName());
    }

    /**
     * Đăng nhập
     */
    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO loginDTO) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsernameOrEmail(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Lấy thông tin user
        User user = userRepository.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(), loginDTO.getUsernameOrEmail())
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại"));

        // Kiểm tra trạng thái user
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new IllegalStateException("Tài khoản đã bị khóa hoặc không hoạt động");
        }

        // Generate JWT token
        String token = jwtUtils.generateToken(user.getUsername());

        return new LoginResponseDTO(token, user.getId(), user.getUsername(), user.getEmail(), user.getFullName());
    }

    /**
     * Quên mật khẩu - Gửi email reset
     */
    @Transactional
    public void forgotPassword(ForgotPasswordRequestDTO forgotPasswordDTO) {
        User user = userRepository.findByEmail(forgotPasswordDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email không tồn tại trong hệ thống"));

        // Tạo reset token
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);

        passwordResetTokenRepository.save(resetToken);

        // Gửi email
        sendResetPasswordEmail(user.getEmail(), token);
    }

    /**
     * Reset mật khẩu
     */
    @Transactional
    public void resetPassword(ResetPasswordRequestDTO resetPasswordDTO) {
        // Validate passwords match
        if (!resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Mật khẩu xác nhận không khớp");
        }

        // Find and validate token
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(resetPasswordDTO.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Token không hợp lệ"));

        if (resetToken.getUsed()) {
            throw new IllegalArgumentException("Token đã được sử dụng");
        }

        if (resetToken.isExpired()) {
            throw new IllegalArgumentException("Token đã hết hạn");
        }

        // Update password
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userRepository.save(user);

        // Mark token as used
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
    }

    /**
     * Gửi email reset password
     */
    private void sendResetPasswordEmail(String email, String token) {
        String resetUrl = "http://localhost:8080/api/auth/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset Password Request");
        message.setText("Để reset mật khẩu của bạn, vui lòng click vào link sau:\n" + resetUrl
                + "\n\nLink này sẽ hết hạn sau 24 giờ.");

        mailSender.send(message);
    }

    /**
     * Clean up expired tokens (nên chạy định kỳ)
     */
    @Transactional
    public void cleanUpExpiredTokens() {
        passwordResetTokenRepository.deleteByExpiryDateBefore(LocalDateTime.now());
    }
}

