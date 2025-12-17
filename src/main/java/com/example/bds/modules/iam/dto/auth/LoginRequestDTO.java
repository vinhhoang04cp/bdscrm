package com.example.bds.modules.iam.dto.auth;

import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequestDTO {
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @NotBlank(message = "Username hoặc email không được để trống")
    private String usernameOrEmail;
}





