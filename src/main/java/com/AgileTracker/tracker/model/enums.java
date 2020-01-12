package com.AgileTracker.tracker.model;

public class enums {
    public enum projectState{
        NOT_STARTED,
        STARTED,
        IN_REVIEW,
        FINISHED;
    }

    public enum taskState{
        PENDING,
        IN_PROGRESS,
        COMPLETED;
    }

    public enum okrState{
        NOT_STARTED,
        IN_PROGRESS,
        FINISHED;
    }
}
