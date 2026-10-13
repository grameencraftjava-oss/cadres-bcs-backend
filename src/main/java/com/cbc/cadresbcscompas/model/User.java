package com.cbc.cadresbcscompas.model;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Column(unique = true)
    private String email;
    private String password;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String studentId;
    
    private String university;
    private String studentStatus;
    private String statusYear;

    @Column(columnDefinition = "LONGTEXT")
    private String photoUrl; // স্থায়ীভাবে প্রোফাইল ছবি সেভ রাখার কলাম

    @PrePersist
    public void generateStudentId() {
        if (this.studentId == null) {
            this.studentId = "CBC-" + (int)(Math.random() * 900000 + 100000); 
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getUniversity() { return university; }
    public void setUniversity(String university) { this.university = university; }
    public String getStudentStatus() { return studentStatus; }
    public void setStudentStatus(String studentStatus) { this.studentStatus = studentStatus; }
    public String getStatusYear() { return statusYear; }
    public void setStatusYear(String statusYear) { this.statusYear = statusYear; }
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
}
