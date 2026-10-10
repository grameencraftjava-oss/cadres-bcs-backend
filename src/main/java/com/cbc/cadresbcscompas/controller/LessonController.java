package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.dto.LessonRequest;
import com.cbc.cadresbcscompas.model.Lesson;
import com.cbc.cadresbcscompas.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @PostMapping("/add")
    public String addLesson(@RequestBody LessonRequest request) {
        return lessonService.addLesson(request);
    }

    @GetMapping("/course/{courseId}")
    public List<Lesson> getLessonsByCourse(@PathVariable Long courseId) {
        return lessonService.getLessonsByCourse(courseId);
    }
}
