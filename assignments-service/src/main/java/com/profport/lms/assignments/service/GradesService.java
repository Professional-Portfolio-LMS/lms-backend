package com.profport.lms.assignments.service;

import org.springframework.stereotype.Service;

import com.profport.lms.assignments.repository.SubmissionRepository;

import java.util.List;
import java.util.UUID;

import com.profport.lms.assignments.dto.GradeRequestDTO;
import com.profport.lms.assignments.model.Submission;
import com.profport.lms.assignments.dto.GradeResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GradesService {
    private final SubmissionRepository submissionRepository;
    public GradeResponseDTO calculateOverallGrade(UUID studentId, UUID courseId, GradeRequestDTO gradeRequestDTO) {
        List<Submission> submissions = submissionRepository.findByStudentIdAndAssignment_CourseId(gradeRequestDTO.getStudentId(), gradeRequestDTO.getCourseId());
        if(submissions.isEmpty()) {
            return new GradeResponseDTO(0.0f); // or throw an exception if you prefer
            // throw new IllegalArgumentException("No submissions found for student in this course");
        }
        float totalGrade = 0.0f;
        int count = 0;
        for (Submission submission : submissions) {
            if (submission.getGrade() != null) {
                totalGrade += submission.getGrade();
                count++;
            }
        }
        GradeResponseDTO gradeResponseDTO = new GradeResponseDTO();
        gradeResponseDTO.setOverallGrade(count > 0 ? totalGrade / count : 0.0f);
        return gradeResponseDTO;
    }
}