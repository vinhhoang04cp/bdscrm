package com.example.bds.modules.iam.mapper;

import com.example.bds.modules.iam.dto.Team.CreateTeamDTO;
import com.example.bds.modules.iam.dto.Team.TeamDTO;
import com.example.bds.modules.iam.dto.Team.UpdateTeamDTO;
import com.example.bds.modules.iam.entity.Team;
import com.example.bds.modules.iam.entity.TeamStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TeamMapper {

    // Chuyển đổi từ Team entity sang TeamDTO
    public TeamDTO toDTO(Team team) {
        if (team == null) {
            return null;
        }

        // Khởi tạo đối tượng TeamDTO và thiết lập các thuộc tính
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setCode(team.getCode());
        dto.setTeamname(team.getTeamname());
        dto.setDescription(team.getDescription());
        dto.setStatus(team.getStatus() != null ? team.getStatus().name() : null);
        dto.setCreatedAt(team.getCreatedAt());
        dto.setUpdatedAt(team.getUpdatedAt());
        return dto;
    }

    // Chuyển đổi từ CreateTeamDTO sang Team entity
    public Team toEntity(CreateTeamDTO createTeamDTO) {
        if (createTeamDTO == null) {
            return null;
        }

        Team team = new Team();
        team.setCode(createTeamDTO.getCode());
        team.setTeamname(createTeamDTO.getTeamname());
        team.setDescription(createTeamDTO.getDescription());
        team.setCreatedAt(LocalDateTime.now());
        return team;
    }

    // Chuyển đổi từ UpdateTeamDTO sang Team entity
    public Team toEntity(UpdateTeamDTO updateTeamDTO) {
        if (updateTeamDTO == null) {
            return null;
        }

        Team team = new Team();
        team.setId(updateTeamDTO.getId());
        team.setCode(updateTeamDTO.getCode());
        team.setTeamname(updateTeamDTO.getTeamname());
        team.setDescription(updateTeamDTO.getDescription());
        team.setUpdatedAt(LocalDateTime.now());
        return team;
    }

    /**
     * Cập nhật Team entity từ UpdateTeamDTO
     */
    public void updateEntity(Team team, UpdateTeamDTO updateTeamDTO) {
        if (team == null || updateTeamDTO == null) {
            return;
        }

        if (updateTeamDTO.getTeamname() != null) {
            team.setTeamname(updateTeamDTO.getTeamname());
        }
        if (updateTeamDTO.getCode() != null) {
            team.setCode(updateTeamDTO.getCode());
        }
        if (updateTeamDTO.getDescription() != null) {
            team.setDescription(updateTeamDTO.getDescription());
        }
        if (updateTeamDTO.getStatus() != null) {
            team.setStatus(TeamStatus.valueOf(updateTeamDTO.getStatus()));
        }
        team.setUpdatedAt(LocalDateTime.now());
    }
}
