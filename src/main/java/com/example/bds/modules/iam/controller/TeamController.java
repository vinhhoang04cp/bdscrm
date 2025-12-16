package com.example.bds.modules.iam.controller;

import com.example.bds.modules.iam.dto.Team.CreateTeamDTO;
import com.example.bds.modules.iam.dto.Team.TeamDTO;
import com.example.bds.modules.iam.dto.Team.UpdateTeamDTO;
import com.example.bds.modules.iam.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/teams")
@Tag(name = "Team Management", description = "APIs quản lý nhóm/team trong hệ thống IAM")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams(@RequestParam(required = false) String status) {
        List<TeamDTO> teams;
        if (status != null && !status.isEmpty()) {
            teams = teamService.getTeamsByStatus(status);
        } else {
            teams = teamService.getAllTeams();
        }
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<TeamDTO> getTeamByCode(@PathVariable String code) {
        try {
            TeamDTO teamDTO = teamService.getTeamByCode(code);
            return ResponseEntity.ok(teamDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/teamname/{teamname}")
    public ResponseEntity<TeamDTO> getTeamByTeamname(@PathVariable String teamname) {
        try {
            TeamDTO teamDTO = teamService.getTeamByTeamname(teamname);
            return ResponseEntity.ok(teamDTO);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
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
