package com.AgileTracker.tracker.exceptions;

public class TaskNotFoundException extends GenericException {
    public TaskNotFoundException(){
        super("Error!! Task not found!");
    }
}
