package com.profport.lms.user.dto;

import com.profport.lms.user.model.UserRole;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String password; // plain text coming in
    private UserRole role;
}
