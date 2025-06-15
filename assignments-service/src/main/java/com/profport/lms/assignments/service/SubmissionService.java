package com.profport.lms.assignments.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.profport.lms.assignments.dto.SubmissionRequestDTO;
import com.profport.lms.assignments.dto.SubmissionResponseDTO;
import com.profport.lms.assignments.mapper.SubmissionMapper;
import com.profport.lms.assignments.model.Assignment;
import com.profport.lms.assignments.model.Submission;
import com.profport.lms.assignments.model.SubmissionFile;
import com.profport.lms.assignments.repository.AssignmentRepository;
import com.profport.lms.assignments.repository.SubmissionFileRepository;
import com.profport.lms.assignments.repository.SubmissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepo;
    private final SubmissionFileRepository submissionFileRepo;
    @Autowired
    private AssignmentRepository assignmentRepo;

    public SubmissionResponseDTO submit(UUID assignmentId, UUID studentId, SubmissionRequestDTO dto, List<MultipartFile> files) {
        Assignment assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        Submission submission = submissionRepo.save(Submission.builder()
                .assignment(assignment)  
                .studentId(studentId)
                .comment(dto.getComment())
                .build());

        for (MultipartFile file : files) {
            String url = uploadToS3(file);
            submissionFileRepo.save(SubmissionFile.builder()
                    .submission(submission)
                    .fileUrl(url)
                    .build());
        }

        return SubmissionMapper.toDTO(submission);
    }

    private String uploadToS3(MultipartFile file) {
        return "https://s3.bucket/" + file.getOriginalFilename();
    }
}

