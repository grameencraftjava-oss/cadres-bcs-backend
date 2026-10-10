package com.cbc.cadresbcscompas.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MyCourseResponse {
    private Long enrollmentId;
    private Long courseId;
    private String courseTitle;
    private String instructorName;
    private LocalDateTime enrollmentDate;
}
