package com.profport.lms.assignments.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.profport.lms.assignments.dto.AssignmentRequestDTO;
import com.profport.lms.assignments.dto.AssignmentResponseDTO;
import com.profport.lms.assignments.service.AssignmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentService service;

    @PostMapping
    public AssignmentResponseDTO create(
            @RequestPart AssignmentRequestDTO dto,
            @RequestPart(required = false) List<MultipartFile> files) {
        System.out.println("Skibidi1 " + dto);
        System.out.println("Skibidi2" + files);
        return service.createAssignment(dto, files != null ? files : List.of());
    }

    @GetMapping("/course/{courseId}")
    public List<AssignmentResponseDTO> byCourse(@PathVariable UUID courseId) {
        return service.getByCourse(courseId);
    }
}
