package com.profport.lms.assignments.mapper;

import java.util.Collections;

import com.profport.lms.assignments.dto.AssignmentRequestDTO;
import com.profport.lms.assignments.dto.AssignmentResponseDTO;
import com.profport.lms.assignments.model.Assignment;

public class AssignmentMapper {
    public static Assignment toEntity(AssignmentRequestDTO dto) {
        return Assignment.builder()
                .courseId(dto.getCourseId())
                .type(dto.getType())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .dueDate(dto.getDueDate())
                .maxScore(dto.getMaxScore())
                .build();
    }

    public static AssignmentResponseDTO toDTO(Assignment assignment) {
        return AssignmentResponseDTO.builder()
                .id(assignment.getId())
                .courseId(assignment.getCourseId())
                .type(assignment.getType())
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .dueDate(assignment.getDueDate())
                .maxScore(assignment.getMaxScore())
                .createdAt(assignment.getCreatedAt())
                .fileUrls(Collections.emptyList()) // or populate if needed
                .build();
    }
}
