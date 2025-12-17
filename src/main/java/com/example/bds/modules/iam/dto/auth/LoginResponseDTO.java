package com.example.bds.modules.iam.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;
    private String email;
    private String fullName;

    public LoginResponseDTO(String token, Long userId, String username, String email, String fullName) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
    }
}

