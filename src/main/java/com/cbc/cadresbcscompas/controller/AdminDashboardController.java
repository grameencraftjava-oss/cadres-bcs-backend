package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.model.User;
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
        // Return dummy stats for others until fully implemented
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalSignups", totalSignups);
        stats.put("totalStudents", totalSignups); // Assuming all are students for now
        stats.put("activeEnrollments", 0L);
        stats.put("totalReviews", 0L);
        stats.put("bannedAccounts", 0L);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory() {
        // Return the actual login history saved in DB
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
            // Add phone if it exists in your User model
            return ResponseEntity.ok(data);
        }
        return ResponseEntity.notFound().build();
    }
}
