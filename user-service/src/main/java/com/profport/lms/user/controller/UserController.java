package com.profport.lms.user.controller;

import com.profport.lms.user.dto.LoginRequestDTO;
import com.profport.lms.user.dto.LoginResponseDTO;
import com.profport.lms.user.dto.UserRequestDTO;
import com.profport.lms.user.dto.UserResponseDTO;
import com.profport.lms.user.model.User;
import com.profport.lms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRequestDTO dto) {
        return service.register(dto);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO dto) {
        return service.login(dto);
    }

    @GetMapping
    public List<UserResponseDTO> allUsers() {
        return service.listAll();
    }

    // utility endpoint for other services (course-service) to fetch user by id
    @GetMapping("/{id}")
    public UserResponseDTO userById(@PathVariable UUID id) {
        User u = service.getById(id);
        return new UserResponseDTO(u.getId(), u.getName(), u.getEmail(), u.getRole(), u.getCreatedAt());
    }
}
