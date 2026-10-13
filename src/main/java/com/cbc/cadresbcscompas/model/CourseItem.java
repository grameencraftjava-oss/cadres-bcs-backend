package com.cbc.cadresbcscompas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CourseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Double courseFee;
    
    @Column(columnDefinition = "TEXT")
    private String fullInfo;
    
    @Column(columnDefinition = "TEXT")
    private String routine;
    
    @Column(columnDefinition = "TEXT")
    private String modules;
    
    @Column(columnDefinition = "TEXT")
    private String studyPlan;
    
    @Column(columnDefinition = "TEXT")
    private String mentors;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Double getCourseFee() { return courseFee; }
    public void setCourseFee(Double courseFee) { this.courseFee = courseFee; }
    public String getFullInfo() { return fullInfo; }
    public void setFullInfo(String fullInfo) { this.fullInfo = fullInfo; }
    public String getRoutine() { return routine; }
    public void setRoutine(String routine) { this.routine = routine; }
    public String getModules() { return modules; }
    public void setModules(String modules) { this.modules = modules; }
    public String getStudyPlan() { return studyPlan; }
    public void setStudyPlan(String studyPlan) { this.studyPlan = studyPlan; }
    public String getMentors() { return mentors; }
    public void setMentors(String mentors) { this.mentors = mentors; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
