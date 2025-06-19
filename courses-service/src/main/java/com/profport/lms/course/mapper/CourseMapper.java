package com.profport.lms.course.mapper;

import com.profport.lms.course.dto.CourseResponseDTO;
import com.profport.lms.course.dto.InstructorResponseDTO;
import com.profport.lms.course.model.Course;

public class CourseMapper {

    public static CourseResponseDTO toResponseDTO(Course course) {
        return CourseResponseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .category(course.getCategory())
                .createdAt(course.getCreatedAt())
                .instructor(
                        InstructorResponseDTO.builder()
                                .id(course.getInstructor().getId())
                                .name(course.getInstructor().getName())
                                .email(course.getInstructor().getEmail())
                                .build())
                .build();
    }
}
