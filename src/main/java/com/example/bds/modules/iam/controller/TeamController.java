package com.example.bds.modules.iam.controller;

import com.example.bds.modules.iam.dto.Team.CreateTeamDTO;
import com.example.bds.modules.iam.dto.Team.TeamDTO;
import com.example.bds.modules.iam.dto.Team.UpdateTeamDTO;
import com.example.bds.modules.iam.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // Đánh dấu lớp này là một REST controller
@RequestMapping("/api/teams") // Định nghĩa đường dẫn cơ sở cho tất cả các endpoint trong controller này
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() { // Hàm sẽ trả về danh sách TeamDTO
        List<TeamDTO> teams = teamService.getAllTeams(); // Gọi đến TeamService để lấy danh sách teams
        return ResponseEntity.ok(teams); // Trả về danh sách teams với mã trạng thái 200 OK
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody CreateTeamDTO createTeamDTO) {
        try {
            TeamDTO teamDTO = teamService.createTeam(createTeamDTO);
            return new ResponseEntity<>(teamDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id, @RequestBody UpdateTeamDTO updateTeamDTO) {
        try {
            TeamDTO teamDTO = teamService.updateTeam(id, updateTeamDTO);
            return ResponseEntity.ok(teamDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        try {
            teamService.deleteTeam(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
