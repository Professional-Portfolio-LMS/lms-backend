package com.profport.lms.course.dto;

import com.profport.lms.course.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private UserRole role;
}

