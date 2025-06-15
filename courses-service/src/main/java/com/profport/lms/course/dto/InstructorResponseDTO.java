package com.profport.lms.course.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstructorResponseDTO {
    private UUID id;
    private String name;
    private String email;
}
