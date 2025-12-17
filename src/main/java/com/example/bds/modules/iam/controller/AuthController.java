package com.example.bds.modules.iam.controller;

import com.example.bds.modules.iam.dto.auth.*;
import com.example.bds.modules.iam.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API xác thực và quản lý tài khoản")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Đăng ký tài khoản mới")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerDTO) {
        try {
            LoginResponseDTO response = authService.register(registerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Đăng nhập")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        try {
            LoginResponseDTO response = authService.login(loginDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Username/Email hoặc mật khẩu không chính xác");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Quên mật khẩu - Gửi email reset")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO forgotPasswordDTO) {
        try {
            authService.forgotPassword(forgotPasswordDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email reset mật khẩu đã được gửi");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset mật khẩu")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO resetPasswordDTO) {
        try {
            authService.resetPassword(resetPasswordDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Mật khẩu đã được reset thành công");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}

