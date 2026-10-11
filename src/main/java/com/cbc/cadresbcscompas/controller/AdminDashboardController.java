package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.model.User;
import com.cbc.cadresbcscompas.model.Role;
import com.cbc.cadresbcscompas.repository.UserRepository;
import com.cbc.cadresbcscompas.repository.LoginHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminDashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginHistoryRepository historyRepository;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        long totalSignups = userRepository.count();
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalSignups", totalSignups);
        stats.put("totalStudents", totalSignups); 
        stats.put("activeEnrollments", 0L);
        stats.put("totalReviews", 0L);
        stats.put("bannedAccounts", 0L);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/signups")
    public ResponseEntity<?> getAllSignups() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory() {
        return ResponseEntity.ok(historyRepository.findAll());
    }

    @GetMapping("/student-by-id")
    public ResponseEntity<?> getStudentById(@RequestParam Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Map<String, String> data = new HashMap<>();
            data.put("name", user.getName());
            data.put("email", user.getEmail());
            return ResponseEntity.ok(data);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> manualEnroll(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok("Student enrolled successfully.");
    }

    @PostMapping("/vouchers")
    public ResponseEntity<?> createVoucher(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok("Voucher created successfully.");
    }

    @PostMapping("/assign-role")
    public ResponseEntity<?> assignRole(@RequestBody Map<String, String> payload) {
        String email = payload.get("adminEmail");
        String roleStr = payload.get("role");
        
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            try {
                user.setRole(Role.valueOf(roleStr));
                userRepository.save(user);
                return ResponseEntity.ok("Role updated successfully.");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Invalid Role.");
            }
        }
        return ResponseEntity.badRequest().body("User not found with this email.");
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        return ResponseEntity.ok("Review deleted.");
    }
}
