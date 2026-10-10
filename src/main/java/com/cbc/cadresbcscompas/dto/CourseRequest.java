package com.cbc.cadresbcscompas.dto;

import lombok.Data;

@Data
public class CourseRequest {
    private String title;
    private String description;
    private Double price;
    private String instructorName;
}
