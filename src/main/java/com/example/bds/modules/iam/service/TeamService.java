package com.example.bds.modules.iam.service;

import com.example.bds.modules.iam.entity.Team;
import com.example.bds.modules.iam.repository.TeamRepository;
import com.example.bds.modules.iam.dto.Team.TeamDTO;
import com.example.bds.modules.iam.dto.Team.CreateTeamDTO;
import com.example.bds.modules.iam.dto.Team.UpdateTeamDTO;
import com.example.bds.modules.iam.mapper.TeamMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Đánh dấu lớp này là một Service trong Spring Boot
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    // Lấy tất cả teams và chuyển đổi sang TeamDTO
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(teamMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Tạo một team mới từ CreateTeamDTO
    public TeamDTO createTeam(CreateTeamDTO createTeamDTO) {
        if(teamRepository.existsByCode(createTeamDTO.getCode())) {
            throw new IllegalArgumentException("Team code already exists");
        }
        if(teamRepository.existsByTeamname(createTeamDTO.getTeamname())) {
            throw new IllegalArgumentException("Team name already exists");
        }

        Team team = teamMapper.toEntity(createTeamDTO);
        Team savedTeam = teamRepository.save(team);
        return teamMapper.toDTO(savedTeam);
    }

    // Update một team từ UpdateTeamDTO
    public TeamDTO updateTeam(Long id, UpdateTeamDTO updateTeamDTO) {
        // Tìm team theo id
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + id));

        // Kiểm tra code có bị trùng với team khác không
        if (updateTeamDTO.getCode() != null && !updateTeamDTO.getCode().equals(team.getCode())) {
            if (teamRepository.existsByCode(updateTeamDTO.getCode())) {
                throw new IllegalArgumentException("Team code already exists");
            }
        }

        // Kiểm tra teamname có bị trùng với team khác không
        if (updateTeamDTO.getTeamname() != null && !updateTeamDTO.getTeamname().equals(team.getTeamname())) {
            if (teamRepository.existsByTeamname(updateTeamDTO.getTeamname())) {
                throw new IllegalArgumentException("Team name already exists");
            }
        }

        // Cập nhật thông tin team
        teamMapper.updateEntity(team, updateTeamDTO);

        // Lưu và trả về DTO
        Team updatedTeam = teamRepository.save(team);
        return teamMapper.toDTO(updatedTeam);
    }

    // Xoá team theo id
    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new IllegalArgumentException("Team not found with id: " + id);
        }
        teamRepository.deleteById(id);
    }
}
