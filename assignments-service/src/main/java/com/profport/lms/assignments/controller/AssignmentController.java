package com.profport.lms.assignments.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.profport.lms.assignments.dto.AssignmentRequestDTO;
import com.profport.lms.assignments.dto.AssignmentResponseDTO;
import com.profport.lms.assignments.model.AssignmentType;
import com.profport.lms.assignments.service.AssignmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentService service;

    @PostMapping
    public AssignmentResponseDTO create(
            @RequestParam UUID courseId,
            @RequestParam String type,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String dueDate,
            @RequestParam Integer maxScore,
            @RequestPart(required = false) List<MultipartFile> files) throws IOException {

        AssignmentRequestDTO dto = AssignmentRequestDTO.builder()
                .courseId(courseId)
                .type(AssignmentType.valueOf(type))
                .title(title)
                .description(description)
                .dueDate(Instant.parse(dueDate))
                .maxScore(maxScore)
                .build();

        return service.createAssignment(dto, files != null ? files : List.of());
    }

    @GetMapping("/course/{courseId}")
    public List<AssignmentResponseDTO> byCourse(@PathVariable UUID courseId) {
        return service.getByCourse(courseId);
    }

    @GetMapping("/course/{courseId}/assignment/{assignmentId}")
    public AssignmentResponseDTO byCourseAndAssignmentId(@PathVariable UUID courseId, @PathVariable UUID assignmentId) {
        return service.getByCourseAndAssignment(courseId, assignmentId);
    }
}
