package com.example.bds.modules.iam.dto.User;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
public class UpdateUserDTO {
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private String password;
    private String teamname;
    private String avtarUrl;
}
