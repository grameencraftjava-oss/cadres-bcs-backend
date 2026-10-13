package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.model.CourseItem;
import com.cbc.cadresbcscompas.model.PortalContent;
import com.cbc.cadresbcscompas.repository.CourseItemRepository;
import com.cbc.cadresbcscompas.repository.PortalContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portal")
@CrossOrigin(origins = "*")
public class PortalDataController {

    @Autowired
    private CourseItemRepository courseItemRepository;

    @Autowired
    private PortalContentRepository portalContentRepository;

    @GetMapping("/courses")
    public ResponseEntity<List<CourseItem>> getAllCourses() {
        return ResponseEntity.ok(courseItemRepository.findAll());
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<PortalContent>> getSuggestions() {
        return ResponseEntity.ok(portalContentRepository.findByCategoryOrderByCreatedAtDesc("SUGGESTION"));
    }

    @GetMapping("/youtube-classes")
    public ResponseEntity<List<PortalContent>> getYoutubeClasses() {
        return ResponseEntity.ok(portalContentRepository.findByCategoryOrderByCreatedAtDesc("YOUTUBE_CLASS"));
    }
}
