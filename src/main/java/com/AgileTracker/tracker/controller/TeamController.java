package com.AgileTracker.tracker.controller;


import com.AgileTracker.tracker.exceptions.GenericException;
import com.AgileTracker.tracker.model.OKR;
import com.AgileTracker.tracker.model.Project;
import com.AgileTracker.tracker.model.Team;
import com.AgileTracker.tracker.model.User;
import com.AgileTracker.tracker.repository.OKRRepository;
import com.AgileTracker.tracker.repository.TeamRepository;
import com.AgileTracker.tracker.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/team")
@JsonIgnoreProperties(value={"manager"}, allowGetters = true)
public class TeamController {
    @Autowired
    TeamRepository team_repo;

    @Autowired
    UserRepository user_repo;

    @Autowired
    OKRRepository okr_repo;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Team addTeam(@Valid @RequestBody Team team){
        return team_repo.save(team);
    }

    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable(value="id") Long id) throws GenericException {
        return team_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting Team."));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable(value="id") Long id) throws GenericException{
        Team team = team_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting Team."));
        team_repo.delete(team);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{tid}/user")
    public Team addUser(@Valid @RequestBody User user,
                        @PathVariable(value="tid") Long id) throws GenericException {
        Team team = team_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting Team."));
        user.setManager(false);
        team.getMembers().add(user);
        user.setTeam(team);
        return team_repo.save(team);
    }

    @PostMapping("{tid}/manager/{uid}")
    public Team setManager(@PathVariable(value="tid") Long tid,
                           @PathVariable(value="uid") Long uid) throws GenericException {
        Team team = team_repo.findById(tid).orElseThrow(
                () -> new GenericException("Error getting Team."));
        User manager = user_repo.findById(uid).orElseThrow(
                () -> new GenericException("Error getting Team."));
//        team.setManager();
        return team;
    }

    @PostMapping("{tid}/okr/{oid}")
    public Team assingOkr(@PathVariable(value="tid") Long tid,
                           @PathVariable(value="oid") Long oid) throws GenericException {
        Team team = team_repo.findById(tid).orElseThrow(
                () -> new GenericException("Error getting Team."));
        OKR okr = okr_repo.findById(oid).orElseThrow(
                () -> new GenericException("Error getting Team."));
        team.setOkr(okr);
        okr.getTeams().add(team);
        okr_repo.save(okr);
//        team.setManager();
        return team;
    }
}