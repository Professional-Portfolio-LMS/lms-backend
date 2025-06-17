package com.profport.lms.assignments.mapper;

import java.util.List;

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

    public static AssignmentResponseDTO toDTO(Assignment assignment, List<String> fileUrls) {
        return AssignmentResponseDTO.builder()
                .id(assignment.getId())
                .courseId(assignment.getCourseId())
                .type(assignment.getType())
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .dueDate(assignment.getDueDate())
                .maxScore(assignment.getMaxScore())
                .createdAt(assignment.getCreatedAt())
                .fileUrls(fileUrls) // or populate if needed
                .build();
    }
}
