package com.profport.lms.assignments.dto;

import java.time.Instant;
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
public class AssignmentRequestDTO {
    private UUID courseId;  
    private AssignmentType type;
    private String title;
    private String description;
    private Instant dueDate;
    private Integer maxScore;
    // private List<String> fileUrls; // pre-uploaded file URLs (e.g. to S3)
}
