package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.model.User;
import com.cbc.cadresbcscompas.model.Role;
import com.cbc.cadresbcscompas.model.Enrollment;
import com.cbc.cadresbcscompas.repository.UserRepository;
import com.cbc.cadresbcscompas.repository.LoginHistoryRepository;
import com.cbc.cadresbcscompas.repository.EnrollmentRepository;
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

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        long totalSignups = userRepository.count();
        long activeEnrollments = enrollmentRepository.count();
        
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalSignups", totalSignups);
        stats.put("totalStudents", totalSignups); 
        stats.put("activeEnrollments", activeEnrollments);
        stats.put("totalReviews", 0L);
        stats.put("bannedAccounts", 0L);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/signups")
    public ResponseEntity<?> getAllSignups() {
        return ResponseEntity.ok(userRepository.findAll());
    }
    
    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(enrollmentRepository.findAll());
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
            data.put("phone", user.getPhone() != null ? user.getPhone() : "");
            return ResponseEntity.ok(data);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> manualEnroll(@RequestBody Map<String, Object> payload) {
        try {
            String email = payload.getOrDefault("studentEmail", "").toString();
            String courseId = payload.getOrDefault("courseId", "47-preliminary").toString();

            if (enrollmentRepository.existsByStudentEmailAndCourseId(email, courseId)) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "এই স্টুডেন্ট ইতিমধ্যে এই কোর্সে ভর্তি আছে!"));
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setStudentEmail(email);
            enrollment.setStudentName(payload.getOrDefault("studentName", "").toString());
            enrollment.setStudentPhone(payload.getOrDefault("studentPhone", "").toString());
            enrollment.setCourseId(courseId);
            enrollment.setCourseTitle("47th BCS Preliminary");
            enrollment.setInstructorName("Mentors Panel");
            
            if (payload.containsKey("amountPaid") && !payload.get("amountPaid").toString().isEmpty()) {
                enrollment.setAmountPaid(Double.parseDouble(payload.get("amountPaid").toString()));
            } else {
                enrollment.setAmountPaid(0.0);
            }
            
            enrollment.setPaymentMethod(payload.getOrDefault("paymentMethod", "").toString());
            enrollment.setTrxId(payload.getOrDefault("trxId", "").toString());
            
            enrollmentRepository.save(enrollment);
            
            return ResponseEntity.ok(Collections.singletonMap("message", "Student enrolled successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Enrollment failed: " + e.getMessage()));
        }
    }

    @PostMapping("/vouchers")
    public ResponseEntity<?> createVoucher(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(Collections.singletonMap("message", "Voucher created successfully."));
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
                return ResponseEntity.ok(Collections.singletonMap("message", "Role updated successfully."));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid Role."));
            }
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", "User not found with this email."));
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        return ResponseEntity.ok(Collections.singletonMap("message", "Review deleted."));
    }
}
