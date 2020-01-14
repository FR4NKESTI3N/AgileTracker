package com.AgileTracker.tracker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.StringIdGenerator.class,
        property = "id")
public class Task {
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
    private enums.taskState state;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date addDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Getter
    @Setter
    private Date completedDate = new Date();

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private TaskAccept task_accepted;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sprint_id", nullable = false)
    private Sprint sprint;
//    @Getter
//    @Setter
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "sprint_id", nullable = false)
//    private Sprint sprint_plan;
//
//    @Getter
//    @Setter
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "task_accepted", nullable = true)
//    private StandUp standup;

//    @Getter
//    @Setter
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "task_accepted_final", nullable = true)
//    private StandUp review;

}
