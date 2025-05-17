package com.profport.lms.user.service;

import com.profport.lms.user.dto.LoginRequestDTO;
import com.profport.lms.user.dto.LoginResponseDTO;
import com.profport.lms.user.dto.UserRequestDTO;
import com.profport.lms.user.dto.UserResponseDTO;
import com.profport.lms.user.model.User;
import com.profport.lms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public UserResponseDTO register(UserRequestDTO dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .pwHash(encoder.encode(dto.getPassword()))   // HASH HERE
                .role(dto.getRole())
                .build();
        return map(userRepo.save(user));
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!encoder.matches(dto.getPassword(), user.getPwHash()))
            throw new RuntimeException("Bad credentials");
        return new LoginResponseDTO("dummy-token"); // later: generate JWT
    }

    public List<UserResponseDTO> listAll() {
        return userRepo.findAll().stream().map(this::map).toList();
    }

    public User getById(UUID id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private UserResponseDTO map(User u) {
        return UserResponseDTO.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .role(u.getRole())
                .createdAt(u.getCreatedAt())
                .build();
    }
}
