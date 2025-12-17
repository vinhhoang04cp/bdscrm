package com.example.bds.modules.iam.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

/* Hàm DTO dùng để nhận dữ liệu đăng ký tài khoản từ client */
public class RegisterRequestDTO {

    @NotBlank(message = "Username không được để trống") // @NotBlank: Validation không được để trống
    @Size(min = 3, max = 50, message = "Username phải từ 3-50 ký tự") // @Size: Validation độ dài chuỗi
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng") // @Email: Validation định dạng email
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    private String address;
}

