package com.AgileTracker.tracker.controller;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
@JsonIgnoreProperties(value={"status", "actualEndDate"}, allowGetters = true)
public class TaskController {

}
