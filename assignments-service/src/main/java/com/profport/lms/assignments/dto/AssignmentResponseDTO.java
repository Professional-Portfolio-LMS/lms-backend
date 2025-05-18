package com.profport.lms.assignments.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.profport.lms.assignments.model.AssignmentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentResponseDTO {
    private UUID id;
    private UUID courseId;
    private AssignmentType type;
    private String title;
    private String description;
    private Instant dueDate;
    private Integer maxScore;
    private Instant createdAt;
    private List<String> fileUrls;
}
