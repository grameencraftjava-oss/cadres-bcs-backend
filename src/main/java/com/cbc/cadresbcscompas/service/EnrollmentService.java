package com.cbc.cadresbcscompas.service;

import com.cbc.cadresbcscompas.dto.EnrollmentRequest;
import com.cbc.cadresbcscompas.dto.MyCourseResponse;
import com.cbc.cadresbcscompas.model.Enrollment;
import com.cbc.cadresbcscompas.repository.CourseRepository;
import com.cbc.cadresbcscompas.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public String enrollCourse(EnrollmentRequest request) {
        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(request.getUserId());
        enrollment.setCourseId(request.getCourseId());
        
        enrollmentRepository.save(enrollment);
        return "Success: You have successfully enrolled in the course!";
    }

    public List<MyCourseResponse> getStudentDashboard(Long userId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(userId);
        List<MyCourseResponse> dashboard = new ArrayList<>();

        for (Enrollment e : enrollments) {
            courseRepository.findById(e.getCourseId()).ifPresent(course -> {
                MyCourseResponse response = new MyCourseResponse();
                response.setEnrollmentId(e.getId());
                response.setCourseId(course.getId());
                response.setCourseTitle(course.getTitle());
                response.setInstructorName(course.getInstructorName());
                response.setEnrollmentDate(e.getEnrollmentDate());
                dashboard.add(response);
            });
        }
        return dashboard;
    }
}
