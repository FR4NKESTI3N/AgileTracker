package com.AgileTracker.tracker.exceptions;

public class SprintNotFoundException extends GenericException {
    public TaskNotFoundException(){
        super("Error!! Sprint not found!");
    }
}