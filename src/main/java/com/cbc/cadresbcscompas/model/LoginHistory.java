package com.cbc.cadresbcscompas.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String role;
    private String ipDetails;
    private LocalDateTime loginTime;

    // Getters and Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
    public void setIpDetails(String ipDetails) { this.ipDetails = ipDetails; }
    public void setLoginTime(LocalDateTime loginTime) { this.loginTime = loginTime; }
    
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getIpDetails() { return ipDetails; }
    public LocalDateTime getLoginTime() { return loginTime; }
}
