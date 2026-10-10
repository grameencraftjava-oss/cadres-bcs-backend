package com.cbc.cadresbcscompas.service;

import com.cbc.cadresbcscompas.dto.LessonRequest;
import com.cbc.cadresbcscompas.model.Lesson;
import com.cbc.cadresbcscompas.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    public String addLesson(LessonRequest request) {
        Lesson lesson = new Lesson();
        lesson.setTitle(request.getTitle());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setCourseId(request.getCourseId());
        
        lessonRepository.save(lesson);
        return "Success: Lesson added successfully with Google Drive Link!";
    }

    public List<Lesson> getLessonsByCourse(Long courseId) {
        return lessonRepository.findByCourseId(courseId);
    }
}
