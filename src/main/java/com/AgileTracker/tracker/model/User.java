package com.AgileTracker.tracker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.StringIdGenerator.class,
        property = "id")
public class User {
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
    private String email;

    @Getter
    @Setter
    private Boolean manager = false;

//    @Getter
//    @Setter
//    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
//    private List<Team> managedTeams;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;

//    @Getter
//    @Setter
//    @ManyToMany
//    private Team team;
//
//    @Getter
//    @Setter
//    @ManyToOne
//    private Task task;






}