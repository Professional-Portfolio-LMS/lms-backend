package com.profport.lms.assignments.controller;

import com.profport.lms.assignments.dto.GradeRequestDTO;
import com.profport.lms.assignments.dto.GradeResponseDTO;
import com.profport.lms.assignments.security.JwtUtil;
import com.profport.lms.assignments.service.GradesService;

import lombok.RequiredArgsConstructor;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/grades")
@RestController
@RequiredArgsConstructor
public class GradesController {
    private final GradesService gradesService;
    private final JwtUtil jwtUtil;

    @GetMapping("/grades/{courseid}")
    public GradeResponseDTO getOverallGrades(
        @RequestHeader("Authorization") String authHeader, @PathVariable UUID courseId) {
        UUID studentId = jwtUtil.getUserIdFromToken(authHeader.replace("Bearer ", "")); // Assuming the token contains the student ID
        GradeRequestDTO gradeRequestDTO = new GradeRequestDTO();
        GradeResponseDTO overallGrade = gradesService.calculateOverallGrade(studentId, courseId, gradeRequestDTO);
        return overallGrade;
    }
}
