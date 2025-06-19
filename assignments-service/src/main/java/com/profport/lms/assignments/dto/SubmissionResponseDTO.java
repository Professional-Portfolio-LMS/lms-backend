package com.profport.lms.assignments.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionResponseDTO {
    private UUID id;
    private UUID assignmentId;
    private UserResponseDTO student;
    private String comment;
    private Instant submittedAt;
    private Float grade;
    private String instructorComments;
    private List<String> fileUrls;
}