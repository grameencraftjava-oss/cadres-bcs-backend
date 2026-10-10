package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.dto.CourseRequest;
import com.cbc.cadresbcscompas.model.Course;
import com.cbc.cadresbcscompas.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public String addCourse(@RequestBody CourseRequest request) {
        return courseService.addCourse(request);
    }

    @GetMapping("/all")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
}
