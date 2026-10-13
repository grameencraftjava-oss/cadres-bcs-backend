package com.cbc.cadresbcscompas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PortalContent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category; // "SUGGESTION", "YOUTUBE_CLASS", "COURSE_MATERIAL"
    private String courseId; // কোন কোর্সের উপাদান
    
    private boolean isFolder = false; // এটি কি ফোল্ডার নাকি কনটেন্ট?
    private Long parentFolderId; // ফোল্ডারের ভেতর ফোল্ডার সাপোর্ট করার জন্য

    private String subject; // ফোল্ডারের নাম বা বিষয়
    private String topic; // টপিকের নাম
    
    @Column(columnDefinition = "TEXT")
    private String contentText;
    
    @Column(columnDefinition = "TEXT")
    private String pdfUrl;
    
    @Column(columnDefinition = "TEXT")
    private String youtubeUrl;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public boolean isFolder() { return isFolder; }
    public void setFolder(boolean folder) { isFolder = folder; }
    public Long getParentFolderId() { return parentFolderId; }
    public void setParentFolderId(Long parentFolderId) { this.parentFolderId = parentFolderId; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public String getContentText() { return contentText; }
    public void setContentText(String contentText) { this.contentText = contentText; }
    public String getPdfUrl() { return pdfUrl; }
    public void setPdfUrl(String pdfUrl) { this.pdfUrl = pdfUrl; }
    public String getYoutubeUrl() { return youtubeUrl; }
    public void setYoutubeUrl(String youtubeUrl) { this.youtubeUrl = youtubeUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
