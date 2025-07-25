package com.profport.lms.assignments.dto;

import com.profport.lms.assignments.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private UserRole role;
    private Instant createdAt;
}
