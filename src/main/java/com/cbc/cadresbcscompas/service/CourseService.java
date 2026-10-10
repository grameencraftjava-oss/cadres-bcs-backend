package com.cbc.cadresbcscompas.service;

import com.cbc.cadresbcscompas.dto.CourseRequest;
import com.cbc.cadresbcscompas.model.Course;
import com.cbc.cadresbcscompas.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public String addCourse(CourseRequest request) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setInstructorName(request.getInstructorName());
        
        courseRepository.save(course);
        return "Success: Course added successfully!";
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
