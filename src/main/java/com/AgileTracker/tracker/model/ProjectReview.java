package com.AgileTracker.tracker.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "projectreviews")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.StringIdGenerator.class,
        property = "id")
public class ProjectReview {
    @Getter
    @Setter
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Getter
    @Setter
    private String comments;

    @Getter
    @Setter
    @OneToOne
    private Project project;
}