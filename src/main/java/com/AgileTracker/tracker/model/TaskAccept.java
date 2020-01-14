package com.AgileTracker.tracker.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "taskaccepts")
public class TaskAccept {
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Getter
    @Setter
    private Boolean status = false;

    @Getter
    @Setter
    @OneToOne(mappedBy = "task_accepted")
    private Task task;
}
