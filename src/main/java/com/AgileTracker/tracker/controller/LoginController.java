package com.AgileTracker.tracker.controller;

import com.AgileTracker.tracker.exceptions.GenericException;
import com.AgileTracker.tracker.model.Team;
import com.AgileTracker.tracker.model.User;
import com.AgileTracker.tracker.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = { "http://localhost:8080" }, maxAge = 6000)
//@JsonIgnoreProperties(value={"manager"}, allowGetters = true)
public class LoginController {

    @Autowired
    UserRepository user_repo;

    @GetMapping(value = "/{id}/{pwd}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User authenticate(@PathVariable(value="id") Long id,
                             @PathVariable(value="pwd") String pwd) throws GenericException{

        // authenticate

        return user_repo.findById(id).orElseThrow(
                () -> new GenericException("Error getting user."));
    }
}
