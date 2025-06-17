package com.profport.lms.assignments.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionRequestDTO {
    private UUID assignmentId;
    private UUID courseId;
    private UUID studentId;
    private String comment;
    // private List<String> fileUrls; // student’s uploaded file URLs
}
