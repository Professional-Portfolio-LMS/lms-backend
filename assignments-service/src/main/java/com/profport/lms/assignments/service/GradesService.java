package com.profport.lms.assignments.service;

import org.springframework.stereotype.Service;

import com.profport.lms.assignments.repository.SubmissionRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.profport.lms.assignments.dto.GradeRequestDTO;
import com.profport.lms.assignments.model.Submission;
import com.profport.lms.assignments.dto.GradeResponseDTO;
import com.profport.lms.assignments.dto.GradeSubmissionRequestDTO;
import com.profport.lms.assignments.dto.GradeSubmissionResponseDTO;

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
    public GradeSubmissionResponseDTO updateGrade(UUID instructorId, UUID courseId, UUID submissionId, GradeSubmissionRequestDTO gradeSubmissionRequestDTO) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        submission.setGradedAt(Instant.now());
        submission.setGrade(gradeSubmissionRequestDTO.getGrade());
        submission.setInstructorComments(gradeSubmissionRequestDTO.getComment());

        Submission updatedSubmission = submissionRepository.save(submission);

        return GradeSubmissionResponseDTO.builder()
                .submissionId(updatedSubmission.getId())
                .studentId(updatedSubmission.getStudentId())
                .assignmentId(updatedSubmission.getAssignment().getId())
                .grade(updatedSubmission.getGrade())
                .gradedAt(updatedSubmission.getGradedAt())
                .instructorComments(updatedSubmission.getInstructorComments())
                .build();
    }
    public void SubmissionFromLatestToEarliest(UUID studentId){
        List<Submission> submissions = submissionRepository.findByStudentId(studentId);
        
    }
}
