package com.AgileTracker.tracker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "sprints")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.StringIdGenerator.class,
        property = "id")
public class Sprint {
    @Getter
    @Setter
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Getter
    @Setter
    @NotBlank
    private String name;

    @Getter
    @Setter
    @NotBlank
    private enums.sprintState state = enums.sprintState.NOT_STARTED;

//    @Getter
//    @Setter
//    @OneToMany
//    private Task tasks;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Setter
    @Getter
    @OneToOne(mappedBy = "sprint")
    private SprintPlanning planning;

    @Setter
    @Getter
    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL)
    private List<StandUp> standups;

    @Setter
    @Getter
    @OneToOne(mappedBy = "sprint")
    private SprintPlanning review;
}