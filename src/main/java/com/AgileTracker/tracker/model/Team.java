package com.AgileTracker.tracker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.StringIdGenerator.class,
        property = "id")
public class Team {
    @Getter
    @Setter
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "okr_id", nullable = true)
    private OKR okr;

//    @Getter
//    @Setter
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "manager_id", nullable = true)
//    private User manager;

    @Getter
    @Setter
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<User> members;



//    @Getter
//    @Setter
//    @NotBlank
//    private String name;
//
//    @Getter
//    @Setter
//    @OneToOne
//    private Project project;
//
//    @Getter
//    @Setter
//    @ManyToMany
//    private User user;




}