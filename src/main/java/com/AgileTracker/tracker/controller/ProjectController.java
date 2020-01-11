package com.AgileTracker.tracker.controller;

import com.AgileTracker.tracker.exceptions.GenericException;
import com.AgileTracker.tracker.model.Project;
import com.AgileTracker.tracker.model.enums;
import com.AgileTracker.tracker.repository.ProjectRepository;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

enum projectState{
    NOT_STARTED,
    STARTED,
    FINISHED;
}

@RestController
@RequestMapping("/project")
@JsonIgnoreProperties(value={"status", "actualEndDate"}, allowGetters = true)
public class ProjectController {
    @Autowired
    ProjectRepository project_repo;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Project addProject(@Valid @RequestBody Project project){
        project.setState(enums.projectState.NOT_STARTED);
        return project_repo.save(project);
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable(value="id") Long id) throws GenericException{
        return project_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting project."));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable(value="id") Long id) throws GenericException{
        Project project = project_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting project."));
        project_repo.delete(project);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Project editProject(@PathVariable(value="id") Long id, @Valid @RequestBody Project project) throws GenericException{
//        Project project_ = project_repo.findById(id).orElseThrow(
//                () -> new GenericException("Error getting project."));
//        project_repo.
//        return project;
//    }

    @GetMapping("/all")
    public List<Project> getAllProjects(){
        return project_repo.findAll();
    }

    @GetMapping("/{id}/start")
    public ResponseEntity<?> beginReview(@PathVariable(value="id") Long id) throws GenericException{
        Project project = project_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting project."));
        if(project.getState() != enums.projectState.NOT_STARTED)
            throw new GenericException("Invalid operation!");
        project.setState(enums.projectState.STARTED);
        project_repo.save(project);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}/review-start")
    public ResponseEntity<?> beginProject(@PathVariable(value="id") Long id) throws GenericException{
        Project project = project_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting project."));
        if(project.getState() != enums.projectState.STARTED)
            throw new GenericException("Invalid operation!");
        project.setState(enums.projectState.IN_REVIEW);
        project_repo.save(project);
        return ResponseEntity.ok().build();

    }

//    @GetMapping


}
