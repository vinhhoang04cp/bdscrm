package com.example.bds.modules.iam.repository;

import com.example.bds.modules.iam.entity.TeamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bds.modules.iam.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByCode(String code);
    List<Team> findByStatus(TeamStatus status);
    Optional<Team> findByTeamname(String teamname);

    boolean existsByCode(String code);
    boolean existsByTeamname(String teamname);
}
