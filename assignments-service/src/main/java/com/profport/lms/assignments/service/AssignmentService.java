package com.profport.lms.assignments.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.profport.lms.assignments.dto.AssignmentRequestDTO;
import com.profport.lms.assignments.dto.AssignmentResponseDTO;
import com.profport.lms.assignments.mapper.AssignmentMapper;
import com.profport.lms.assignments.model.Assignment;
import com.profport.lms.assignments.model.AssignmentFile;
import com.profport.lms.assignments.repository.AssignmentFileRepository;
import com.profport.lms.assignments.repository.AssignmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepo;
    private final AssignmentFileRepository assignmentFileRepo;

    public AssignmentResponseDTO createAssignment(AssignmentRequestDTO dto, List<MultipartFile> files) {
        Assignment assignment = assignmentRepo.save(AssignmentMapper.toEntity(dto));

        for (MultipartFile file : files) {
            String fileUrl = uploadToS3(file); // your implementation
            assignmentFileRepo.save(AssignmentFile.builder()
                    .assignment(assignment)
                    .fileUrl(fileUrl)
                    .build());
        }

        return AssignmentMapper.toDTO(assignment);
    }

    public List<AssignmentResponseDTO> getByCourse(UUID courseId) {
        return assignmentRepo.findByCourseId(courseId).stream()
                .map(AssignmentMapper::toDTO)
                .toList();
    }

    private String uploadToS3(MultipartFile file) {
        // mock or actual AWS S3 logic
        return "https://s3.bucket/" + file.getOriginalFilename();
    }
}
