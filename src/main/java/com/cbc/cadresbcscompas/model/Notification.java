package com.cbc.cadresbcscompas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String targetType; // "ALL", "COURSE", "INDIVIDUAL"
    private String targetCourseId; // যদি COURSE হয়
    private String targetStudentId; // যদি INDIVIDUAL হয়

    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String message;
    
    @Column(columnDefinition = "TEXT")
    private String linkUrl; // নোটিফিকেশনে লিংক দেওয়ার জন্য
    private String linkText; // বাটন বা লিংকের টেক্সট
    
    private boolean isRead = false;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public String getTargetCourseId() { return targetCourseId; }
    public void setTargetCourseId(String targetCourseId) { this.targetCourseId = targetCourseId; }
    public String getTargetStudentId() { return targetStudentId; }
    public void setTargetStudentId(String targetStudentId) { this.targetStudentId = targetStudentId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getLinkUrl() { return linkUrl; }
    public void setLinkUrl(String linkUrl) { this.linkUrl = linkUrl; }
    public String getLinkText() { return linkText; }
    public void setLinkText(String linkText) { this.linkText = linkText; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
