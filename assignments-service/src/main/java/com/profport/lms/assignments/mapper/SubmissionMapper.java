package com.profport.lms.assignments.mapper;

import java.util.List;
import com.profport.lms.assignments.dto.SubmissionResponseDTO;
import com.profport.lms.assignments.dto.UserResponseDTO;
import com.profport.lms.assignments.model.Submission;
import com.profport.lms.assignments.model.User;

public class SubmissionMapper {

    public static SubmissionResponseDTO toDTO(Submission submission, List<String> fileUrls,
            UserResponseDTO student) {
        return SubmissionResponseDTO.builder()
                .id(submission.getId())
                .assignmentId(submission.getAssignment().getId())
                .student(student)
                .comment(submission.getComment())
                .submittedAt(submission.getSubmittedAt())
                .grade(submission.getGrade())
                .instructorComments(submission.getInstructorComments())
                .fileUrls(fileUrls)
                .build();
    }

    public static UserResponseDTO mapUserToDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
