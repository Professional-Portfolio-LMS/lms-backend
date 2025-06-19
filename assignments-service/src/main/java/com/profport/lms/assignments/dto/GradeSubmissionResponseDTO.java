package com.profport.lms.assignments.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeSubmissionResponseDTO {
    private UUID submissionId;
    private UUID studentId;
    private UUID assignmentId;
    private Float grade;
    private String instructorComments;
    private Instant gradedAt;
}
