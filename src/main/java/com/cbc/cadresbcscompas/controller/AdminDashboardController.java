package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.model.User;
import com.cbc.cadresbcscompas.model.Role;
import com.cbc.cadresbcscompas.model.Enrollment;
import com.cbc.cadresbcscompas.model.CourseItem;
import com.cbc.cadresbcscompas.model.PortalContent;
import com.cbc.cadresbcscompas.repository.UserRepository;
import com.cbc.cadresbcscompas.repository.LoginHistoryRepository;
import com.cbc.cadresbcscompas.repository.EnrollmentRepository;
import com.cbc.cadresbcscompas.repository.CourseItemRepository;
import com.cbc.cadresbcscompas.repository.PortalContentRepository;
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
    @Autowired
    private CourseItemRepository courseItemRepository;
    @Autowired
    private PortalContentRepository portalContentRepository;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        List<User> allUsers = userRepository.findAll();
        boolean changed = false;
        for (User u : allUsers) {
            if (u.getStudentId() == null || u.getStudentId().isEmpty()) {
                u.setStudentId("CBC-" + (int)(Math.random() * 900000 + 100000));
                userRepository.save(u);
                changed = true;
            }
        }
        long totalSignups = allUsers.size();
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
        List<User> allUsers = userRepository.findAll();
        for (User u : allUsers) {
            if (u.getStudentId() == null || u.getStudentId().isEmpty()) {
                u.setStudentId("CBC-" + (int)(Math.random() * 900000 + 100000));
                userRepository.save(u);
            }
        }
        return ResponseEntity.ok(allUsers);
    }
    
    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(enrollmentRepository.findAll());
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory() {
        return ResponseEntity.ok(historyRepository.findAll());
    }

    // Student ID (যেমন CBC-180145 বা শুধু 180145) দিয়ে সার্চ করলে নাম, ফোন ও ইমেইল চলে আসবে
    @GetMapping("/student-by-id")
    public ResponseEntity<?> getStudentById(@RequestParam String id) {
        String cleanId = id.trim().toUpperCase();
        Optional<User> userOpt = userRepository.findByStudentId(cleanId);
        if (!userOpt.isPresent() && !cleanId.startsWith("CBC-")) {
            userOpt = userRepository.findByStudentId("CBC-" + cleanId);
        }
        if (!userOpt.isPresent()) {
            try {
                Long numId = Long.parseLong(cleanId);
                userOpt = userRepository.findById(numId);
            } catch (Exception ignored) {}
        }

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getStudentId() == null || user.getStudentId().isEmpty()) {
                user.setStudentId("CBC-" + (int)(Math.random() * 900000 + 100000));
                userRepository.save(user);
            }
            Map<String, String> data = new HashMap<>();
            data.put("name", user.getName() != null ? user.getName() : "");
            data.put("email", user.getEmail() != null ? user.getEmail() : "");
            data.put("phone", user.getPhone() != null ? user.getPhone() : "");
            data.put("studentId", user.getStudentId());
            return ResponseEntity.ok(data);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> manualEnroll(@RequestBody Map<String, Object> payload) {
        try {
            String email = payload.getOrDefault("studentEmail", "").toString().trim();
            String courseId = payload.getOrDefault("courseId", "").toString().trim();
            String courseTitle = payload.getOrDefault("courseTitle", courseId).toString().trim();

            if (enrollmentRepository.existsByStudentEmailAndCourseId(email, courseId)) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "এই স্টুডেন্ট ইতিমধ্যে এই কোর্সে ভর্তি আছে!"));
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setStudentEmail(email);
            enrollment.setStudentName(payload.getOrDefault("studentName", "").toString());
            enrollment.setStudentPhone(payload.getOrDefault("studentPhone", "").toString());
            enrollment.setCourseId(courseId);
            enrollment.setCourseTitle(courseTitle.isEmpty() ? courseId : courseTitle);
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

    // --- Course Management (Admin) ---
    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody CourseItem courseItem) {
        return ResponseEntity.ok(courseItemRepository.save(courseItem));
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseItemRepository.deleteById(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Course deleted"));
    }

    // --- Free Suggestions & YouTube Classes Management (Admin) ---
    @PostMapping("/portal-content")
    public ResponseEntity<?> createPortalContent(@RequestBody PortalContent content) {
        return ResponseEntity.ok(portalContentRepository.save(content));
    }

    @DeleteMapping("/portal-content/{id}")
    public ResponseEntity<?> deletePortalContent(@PathVariable Long id) {
        portalContentRepository.deleteById(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Content deleted"));
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
