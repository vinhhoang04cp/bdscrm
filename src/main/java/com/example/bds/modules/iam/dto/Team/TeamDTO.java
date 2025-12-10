package com.example.bds.modules.iam.dto.Team;

import com.example.bds.modules.iam.dto.User.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
public class TeamDTO {
    private Long id;
    private String teamname;
    private String code;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
