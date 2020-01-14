package com.AgileTracker.tracker.controller;

import com.AgileTracker.tracker.exceptions.GenericException;
import com.AgileTracker.tracker.exceptions.SprintNotFoundException;
import com.AgileTracker.tracker.exceptions.TaskNotFoundException;
import com.AgileTracker.tracker.model.*;
import com.AgileTracker.tracker.repository.SprintRepository;
import com.AgileTracker.tracker.repository.TaskRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;

@RestController
@RequestMapping("/sprint")
@JsonIgnoreProperties(value = {"state"}, allowGetters = true)
public class SprintController {
    @Autowired
    SprintRepository sprint_repo;

    @Autowired
    TaskRepository task_repo;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Sprint addSprint(@Valid @RequestBody Sprint sprint){
        sprint.setState(enums.sprintState.NOT_STARTED);
        sprint_repo.save(sprint);
    }

    @GetMapping("/{id}")
    public Sprint getProjectById(@PathVariable(value="id") Long id) throws GenericException {
        return sprint_repo.findById(id).orElseThrow(() -> new SprintNotFoundException());
    }

    @PostMapping(value = "/{id}/planning")
    public Sprint startPlanning(@PathVariable(value="sid") Long sid, @Valid @RequestBody List<Long> task_list)
            throws TaskNotFoundException {
        Sprint sprint = sprint_repo.findById(sid).orElseThrow(() -> new SprintNotFoundException());

        // todo -- add check so that if addition fails midway, the state changes are reverted
        for(Long id : task_list){
            Task task = task_repo.findById(id).orElse(() -> new TaskNotFoundException());
            task.setState(enums.taskState.IN_PROGRESS);
            sprint.getTasks().add(task);
            task.setSprint(sprint);
        }

        sprint.setState(enums.sprintState.IN_PLANNING);
        sprint.setPlanning(new SprintPlanning());
        return sprint_repo.save(sprint);
    }

    @PostMapping(value = "/{id}/planning/{comment}")
    public Sprint addPlanningComment(@PathVariable(value="sid") Long sid,
                                     @PathVariable(value = "comment") String comment)
            throws TaskNotFoundException{
        Sprint sprint = sprint_repo.findById(sid).orElseThrow(() -> new SprintNotFoundException());
        sprint.getPlanning().setComments(comment);
        return sprint_repo.save(sprint);
    }

}
