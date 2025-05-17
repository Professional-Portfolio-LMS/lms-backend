package com.profport.lms.course.dto;

import com.profport.lms.course.model.CourseCategory;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class CourseResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private CourseCategory category;
    private UUID instructorId;
    private Instant createdAt;
}
