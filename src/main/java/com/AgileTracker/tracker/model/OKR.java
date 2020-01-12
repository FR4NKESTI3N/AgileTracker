package com.AgileTracker.tracker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "okr")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.StringIdGenerator.class,
        property = "id")
public class OKR {
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
    private String details;

    @Getter
    @Setter
    private enums.okrState state;

    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    @CreatedDate
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
    @OneToMany(mappedBy = "okr", cascade = CascadeType.ALL)
    private List<Project> projects;

    @Getter
    @Setter
    @OneToMany(mappedBy = "okr", cascade = CascadeType.ALL)
    List<Team> teams;
}
