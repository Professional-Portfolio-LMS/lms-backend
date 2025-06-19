package com.profport.lms.assignments.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.profport.lms.assignments.dto.SubmissionRequestDTO;
import com.profport.lms.assignments.dto.SubmissionResponseDTO;
import com.profport.lms.assignments.security.JwtUtil;
import com.profport.lms.assignments.service.SubmissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/submissions")
public class SubmissionController {

    private final SubmissionService service;
    private final JwtUtil jwtUtil;

    @PostMapping("{courseId}/{assignmentId}")
    public SubmissionResponseDTO submit(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID assignmentId,
            @PathVariable UUID courseId,
            @RequestParam String comment,
            @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        String token = authHeader.replace("Bearer ", "");
        UUID studentId = jwtUtil.getUserIdFromToken(token);

        SubmissionRequestDTO dto = SubmissionRequestDTO.builder()
                .assignmentId(assignmentId)
                .courseId(courseId)
                .comment(comment)
                .studentId(studentId)
                .build();

        return service.submit(dto, files != null ? files : List.of());
    }

    @GetMapping("{courseId}/{assignmentId}")
    public List<SubmissionResponseDTO> getSubmissions(
            @PathVariable UUID courseId,
            @PathVariable UUID assignmentId) {
        return service.getSubmissions(courseId, assignmentId);
    }

}
