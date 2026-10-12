package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.model.Enrollment;
import com.cbc.cadresbcscompas.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // স্টুডেন্টের ড্যাশবোর্ডে কোর্স দেখানোর API
    @GetMapping("/dashboard/{email}")
    public ResponseEntity<?> getStudentDashboard(@PathVariable String email) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentEmail(email);
        return ResponseEntity.ok(enrollments);
    }
}
