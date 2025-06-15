package com.profport.lms.assignments.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.profport.lms.assignments.dto.SubmissionResponseDTO;
import com.profport.lms.assignments.model.Submission;
import com.profport.lms.assignments.model.SubmissionFile;

public class SubmissionMapper {

    public static SubmissionResponseDTO toDTO(Submission submission) {
        List<String> fileUrls = submission.getFiles() != null
                ? submission.getFiles().stream()
                        .map(SubmissionFile::getFileUrl)
                        .collect(Collectors.toList())
                : null;

        return SubmissionResponseDTO.builder()
                .id(submission.getId())
                .assignmentId(submission.getAssignment() != null ? submission.getAssignment().getId() : null)
                .studentId(submission.getStudentId())
                .comment(submission.getComment())
                .submittedAt(submission.getSubmittedAt())
                .grade(submission.getGrade())
                .instructorComments(submission.getInstructorComments())
                .fileUrls(fileUrls)
                .build();
    }

}
