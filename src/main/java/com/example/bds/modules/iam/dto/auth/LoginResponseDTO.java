package com.example.bds.modules.iam.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

/* Đánh dấu hàm DTO dùng để gửi dữ liệu phản hồi sau khi đăng nhập thành công */

public class LoginResponseDTO {

    /* Các thuộc tính của DTO */
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;
    private String email;
    private String fullName;

    // Constructor với các tham số cần thiết
    public LoginResponseDTO(String token, Long userId, String username, String email, String fullName) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
    }
}

