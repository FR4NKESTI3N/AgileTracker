package com.AgileTracker.tracker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
}
