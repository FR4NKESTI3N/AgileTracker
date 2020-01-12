package com.AgileTracker.tracker.controller;

import com.AgileTracker.tracker.exceptions.GenericException;
import com.AgileTracker.tracker.model.*;
import com.AgileTracker.tracker.repository.ProjectRepository;
import com.AgileTracker.tracker.repository.TaskRepository;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<?> beginProject(@PathVariable(value="id") Long id) throws GenericException{
        Project project = project_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting project."));
        if(project.getState() != enums.projectState.NOT_STARTED)
            throw new GenericException("Invalid operation!");
        project.setState(enums.projectState.STARTED);
        project_repo.save(project);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}/review-start")
    public ResponseEntity<?> beginReview(@PathVariable(value="id") Long id) throws GenericException{
        Project project = project_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting project."));
        if(project.getState() != enums.projectState.STARTED)
            throw new GenericException("Invalid operation!");
        project.setState(enums.projectState.IN_REVIEW);
        project_repo.save(project);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{pid}/tasks/all")
    public List<Task> getAllTasks(@PathVariable(value="pid") Long pid) throws GenericException{
        Project project = project_repo.findById(pid).orElseThrow(
                () -> new GenericException("Error getting project."));
        return project.getTasks();

    }

    @Autowired
    TaskRepository task_repo;

    @PostMapping(value = "/{pid}/tasks/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Task addTask(@PathVariable(value="pid") Long pid,
        @Valid @RequestBody Task task) throws GenericException{
        Project project = project_repo.findById(pid).orElseThrow(
                () -> new GenericException("Error getting project."));
        project.getTasks().add(task);
        task.setProject(project);
        project_repo.save(project);
        return task;
    }

    @GetMapping("/{pid}/tasks/{tid}")
    public Task getTask(@PathVariable(value="pid") Long pid,
                        @PathVariable(value="tid") Long tid) throws GenericException{
        Project project = project_repo.findById(pid).orElseThrow(
                () -> new GenericException("Error getting project."));
        for(Task task : project.getTasks()){
            if(task.getId() == tid)
                return task;
        }
        throw new GenericException("Error getting task");
    }

    @PostMapping("/{pid}/review")
    public ProjectReview doReview(@PathVariable(value="pid") Long pid,
                                   @Valid @RequestBody ProjectReview review) throws GenericException{
        Project project = project_repo.findById(pid).orElseThrow(
                () -> new GenericException("Error getting project."));
        if(project.getState() != enums.projectState.STARTED)
            throw new GenericException("Cant review a project not started.");
        project.setReview(review);
        project_repo.save(project);
        return review;
    }

    @GetMapping("/{pid}/review")
    public ProjectReview getReview(@PathVariable(value="pid") Long pid) throws GenericException{
        Project project = project_repo.findById(pid).orElseThrow(
                () -> new GenericException("Error getting project."));
        return project.getReview();
    }

    @GetMapping("/{pid}/sprints")
    public List<Sprint> getSprints(@PathVariable(value="pid") Long pid) throws GenericException{
        Project project = project_repo.findById(pid).orElseThrow(
                () -> new GenericException("Error getting project."));
        return project.getSprints();
    }
}
