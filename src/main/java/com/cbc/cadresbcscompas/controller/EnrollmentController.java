package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.dto.EnrollmentRequest;
import com.cbc.cadresbcscompas.dto.MyCourseResponse;
import com.cbc.cadresbcscompas.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/add")
    public String enrollCourse(@RequestBody EnrollmentRequest request) {
        return enrollmentService.enrollCourse(request);
    }

    @GetMapping("/dashboard/{userId}")
    public List<MyCourseResponse> getStudentDashboard(@PathVariable Long userId) {
        return enrollmentService.getStudentDashboard(userId);
    }
}
