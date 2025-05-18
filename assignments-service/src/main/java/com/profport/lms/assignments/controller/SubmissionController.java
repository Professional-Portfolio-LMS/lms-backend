package com.profport.lms.assignments.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.profport.lms.assignments.dto.SubmissionRequestDTO;
import com.profport.lms.assignments.dto.SubmissionResponseDTO;
import com.profport.lms.assignments.service.SubmissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/submissions")
public class SubmissionController {

    private final SubmissionService service;

    @PostMapping("/{assignmentId}/student/{studentId}")
    public SubmissionResponseDTO submit(
            @PathVariable UUID assignmentId,
            @PathVariable UUID studentId,
            @RequestPart SubmissionRequestDTO dto,
            @RequestPart(required = false) List<MultipartFile> files) {
        return service.submit(assignmentId, studentId, dto, files != null ? files : List.of());
    }
}
