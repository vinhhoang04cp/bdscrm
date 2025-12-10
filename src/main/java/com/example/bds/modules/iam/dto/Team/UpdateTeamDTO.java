package com.example.bds.modules.iam.dto.Team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
public class UpdateTeamDTO {
    private Long id;
    private String teamname;
    private String code;
    private String description;
    private String status;
}
