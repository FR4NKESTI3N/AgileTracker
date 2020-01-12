package com.AgileTracker.tracker.controller;

import com.AgileTracker.tracker.exceptions.GenericException;
import com.AgileTracker.tracker.model.OKR;
import com.AgileTracker.tracker.model.Project;
import com.AgileTracker.tracker.model.Task;
import com.AgileTracker.tracker.model.enums;
import com.AgileTracker.tracker.repository.OKRRepository;
import com.AgileTracker.tracker.repository.ProjectRepository;
import com.AgileTracker.tracker.repository.TaskRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/okr")
@JsonIgnoreProperties(value={"status", "actualEndDate"}, allowGetters = true)
@CrossOrigin(origins = { "http://localhost:8080" }, maxAge = 6000)
public class OKRController {
    @Autowired
    OKRRepository okr_repo;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OKR addOKR(@Valid @RequestBody OKR okr){
        okr.setState(enums.okrState.NOT_STARTED);
        return okr_repo.save(okr);
    }

    @GetMapping("/{id}")
    public OKR getOKRById(@PathVariable(value="id") Long id) throws GenericException {
        return okr_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting project."));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable(value="id") Long id) throws GenericException{
        OKR okr = okr_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting OKR object."));
        okr_repo.delete(okr);
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
    public List<OKR> getOKR(){
        return okr_repo.findAll();
    }

    @GetMapping("/{id}/start")
    public ResponseEntity<?> beginReview(@PathVariable(value="id") Long id) throws GenericException{
        OKR okr = okr_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting project."));
        if(okr.getState() != enums.okrState.NOT_STARTED)
            throw new GenericException("Invalid operation!");
        okr.setState(enums.okrState.IN_PROGRESS);
        okr_repo.save(okr);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/review-start")
    public ResponseEntity<?> endOKR(@PathVariable(value="id") Long id) throws GenericException{
        OKR okr = okr_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting project."));
        if(okr.getState() != enums.okrState.IN_PROGRESS)
            throw new GenericException("Invalid operation!");
        okr.setState(enums.okrState.FINISHED);
        okr_repo.save(okr);
        return ResponseEntity.ok().build();
    }

    public static <T> Set<T> convertListToSet(List<T> list)
    {
        // create an empty set
        Set<T> set = new HashSet<>();

        // Add each element of list into the set
        for (T t : list)
            set.add(t);

        // return the set
        return set;
    }

    @GetMapping("/{oid}/projects/all")
    public Set<Project> getAllProjects(@PathVariable(value="oid") Long oid) throws GenericException{
        OKR okr = okr_repo.findById(oid).orElseThrow(
                () -> new GenericException("Error getting OKR."));
        Set<Project> temp = convertListToSet(okr.getProjects());
        return temp;
    }

    @PostMapping(value = "/{oid}/projects/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Project addProject(@PathVariable(value="oid") Long oid,
                        @Valid @RequestBody Project project) throws GenericException{
        OKR okr = okr_repo.findById(oid).orElseThrow(
                () -> new GenericException("Error getting OKR."));
        project.setState(enums.projectState.NOT_STARTED);
        okr.getProjects().add(project);
        project.setOkr(okr);
        okr_repo.save(okr);
        return project;
    }

    @GetMapping("/{oid}/projects/{pid}")
    public Project getTask(@PathVariable(value="oid") Long oid,
                        @PathVariable(value="oid") Long pid) throws GenericException{
        OKR okr = okr_repo.findById(oid).orElseThrow(
                () -> new GenericException("Error getting OKR."));
        for(Project project : okr.getProjects()){
            if(project.getId().equals(pid))
                return project;
        }
        throw new GenericException("Error getting project");
    }
}