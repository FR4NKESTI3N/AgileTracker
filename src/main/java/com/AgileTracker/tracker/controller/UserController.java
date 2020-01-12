package com.AgileTracker.tracker.controller;

import com.AgileTracker.tracker.exceptions.GenericException;
import com.AgileTracker.tracker.model.Project;
import com.AgileTracker.tracker.model.Task;
import com.AgileTracker.tracker.model.User;
import com.AgileTracker.tracker.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@JsonIgnoreProperties(value={"manager"}, allowGetters = true)
public class UserController {
    @Autowired
    UserRepository user_repo;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User addUser(@Valid @RequestBody User user){
        user.setManager(false);
        return user_repo.save(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value="id") Long id) throws GenericException {
        return user_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting user."));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value="id") Long id) throws GenericException{
        User user = user_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting project."));
        user_repo.delete(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{uid}/projects")
    public List<Project> getUserProjects(@PathVariable(value="uid") Long id) throws GenericException {
        User user = user_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting project."));
        List<Project> projects = user.getTeam().getOkr().getProjects();
        for(Project project : projects)
            project.setOkr(null);
        return projects;
    }

    @GetMapping("/{uid}/tasks")
    public List<Task> getUserTasks(@PathVariable(value="uid") Long id) throws GenericException {
        User user = user_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting project."));

        List<Task> tasks = user.getTeam().getOkr().getTasks();
        for(Task task : tasks)
            task.setProject(null);
        return tasks;
    }
}
