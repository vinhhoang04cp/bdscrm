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

@Service // Đánh dấu lớp này là một service trong Spring
@RequiredArgsConstructor // Tự động tạo constructor với các trường final
public class AuthService {

    private final UserRepository userRepository; // Repository để thao tác với bảng User
    private final PasswordResetTokenRepository passwordResetTokenRepository; // Repository để thao tác với bảng PasswordResetToken
    private final PasswordEncoder passwordEncoder; // Mã hóa mật khẩu
    private final JwtUtils jwtUtils; // Utils để tạo và xác thực JWT token
    private final AuthenticationManager authenticationManager; // Quản lý xác thực người dùng
    private final JavaMailSender mailSender; // Dịch vụ gửi email

    /**
     * Đăng ký user mới
     * *@Transactional là một annotation trong Spring Framework được sử dụng để quản lý giao dịch (transaction) trong các phương thức của lớp dịch vụ (service layer).
     */
    @Transactional
    public LoginResponseDTO register(RegisterRequestDTO registerDTO) {
        // check bàng cách gọi đến repository để kiểm tra username, email, phone đã tồn tại chưa
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
        user.setUsername(registerDTO.getUsername()); // set username bằng giá trị từ registerDTO
        user.setEmail(registerDTO.getEmail()); // set email bằng giá trị từ registerDTO
         // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setFullName(registerDTO.getFullName());
        user.setPhone(registerDTO.getPhone());
        user.setAddress(registerDTO.getAddress());
        user.setStatus(UserStatus.ACTIVE);

        user = userRepository.save(user); // Lưu user vào database

        // Generate JWT token
        String token = jwtUtils.generateToken(user.getUsername()); //

        return new LoginResponseDTO(token, user.getId(), user.getUsername(), user.getEmail(), user.getFullName());
    }

    /**
     * Đăng nhập
     */
    @Transactional(readOnly = true) // @Transactional đánh dấu bắt đầu 1 giao dịch, readOnly = true chỉ định rằng giao dịch này chỉ để đọc dữ liệu, không thay đổi gì
    public LoginResponseDTO login(LoginRequestDTO loginDTO) { // Dữ liệu trả về là 1 LoginResponseDTO
        /*
         * authenticationManger là hàm , gọi từ Spring Security để xác thực người dùng dựa trên thông tin đăng nhập (username/email và password) được cung cấp trong loginDTO.
         * Nếu xác thực thành công, nó sẽ trả về một đối tượng Authentication chứa thông tin về người dùng đã được xác thực.
         */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsernameOrEmail(),
                        loginDTO.getPassword()
                )
        );

        // Thiết lập thông tin xác thực cho SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Lấy thông tin user bằng cách gọi đến findByUsernameOrEmail từ userRepository
        User user = userRepository.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(), loginDTO.getUsernameOrEmail())
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại"));

        // getStatus gọi từ User để kiểm tra trạng thái tài khoản, nếu không phải ACTIVE thì trả về lỗi
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new IllegalStateException("Tài khoản đã bị khóa hoặc không hoạt động");
        }

        // Sinh ra JWT token bằng cách gọi đến generateToken từ jwtUtils
        String token = jwtUtils.generateToken(user.getUsername());

        // Trả về LoginResponseDTO chứa token và thông tin user
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

