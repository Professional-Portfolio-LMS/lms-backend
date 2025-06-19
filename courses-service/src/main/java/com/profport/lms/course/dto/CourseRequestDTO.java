package com.profport.lms.course.dto;

import com.profport.lms.course.model.CourseCategory;
import lombok.Data;

@Data
public class CourseRequestDTO {
    private String title;
    private String description;
    private CourseCategory category;
}
