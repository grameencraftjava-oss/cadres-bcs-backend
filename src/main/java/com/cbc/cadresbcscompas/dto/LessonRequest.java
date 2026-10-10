package com.cbc.cadresbcscompas.dto;

import lombok.Data;

@Data
public class LessonRequest {
    private String title;
    private String videoUrl;
    private Long courseId;
}
