package com.AgileTracker.tracker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.StringIdGenerator.class,
        property = "id")
public class Project {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Getter
    @Setter
    @NotBlank
    private String name;

    @Getter
    @Setter
    private String details;

    @Getter
    @Setter
    private enums.projectState state;

    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date startDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date expectedEndDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date actualEndDate = new Date();

    @Setter
    @Getter
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @Setter
    @Getter
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Sprint

            > sprints;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "okr_id", nullable = false)
    private OKR okr;

//    @Setter
//    @Getter
//    @OneToOne
//    private User manager;
//
    @Setter
    @Getter
    @OneToOne(mappedBy = "project")
    private ProjectReview review;



}
