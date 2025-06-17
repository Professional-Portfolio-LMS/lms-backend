package com.profport.lms.assignments.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeRequestDTO {
    private UUID studentId;
    private UUID courseId;

} 