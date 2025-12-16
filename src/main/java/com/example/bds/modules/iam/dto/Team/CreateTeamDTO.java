package com.example.bds.modules.iam.dto.Team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamDTO {

    @NotBlank(message = "Team name is required")
    @Size(min = 2, max = 100, message = "Team name must be between 2 and 100 characters")
    private String teamname;

    @NotBlank(message = "Team code is required")
    @Size(min = 2, max = 20, message = "Team code must be between 2 and 20 characters")
    private String code;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}
