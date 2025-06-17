package com.profport.lms.assignments.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.profport.lms.assignments.dto.AssignmentRequestDTO;
import com.profport.lms.assignments.dto.AssignmentResponseDTO;
import com.profport.lms.assignments.mapper.AssignmentMapper;
import com.profport.lms.assignments.model.Assignment;
import com.profport.lms.assignments.model.AssignmentFile;
import com.profport.lms.assignments.repository.AssignmentFileRepository;
import com.profport.lms.assignments.repository.AssignmentRepository;
import com.profport.lms.assignments.util.MultipartInputStreamFileResource;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepo;
    private final AssignmentFileRepository assignmentFileRepo;
    private final RestTemplate restTemplate;

    private final String contentServiceUrl = "http://gateway:8080/content/upload";

    public AssignmentResponseDTO createAssignment(AssignmentRequestDTO dto, List<MultipartFile> files)
            throws IOException {
        Assignment assignment = assignmentRepo.save(AssignmentMapper.toEntity(dto));
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileUrl = uploadViaContentService(file);
            assignmentFileRepo.save(AssignmentFile.builder()
                    .assignment(assignment)
                    .fileUrl(fileUrl)
                    .build());
            System.out.println("Saved file URL: " + fileUrl);
            fileUrls.add(fileUrl);
        }

        return AssignmentMapper.toDTO(assignment, fileUrls);
    }

    public List<AssignmentResponseDTO> getByCourse(UUID courseId) {
        return assignmentRepo.findByCourseId(courseId).stream()
                .map(assignment -> {
                    List<String> fileUrls = assignmentFileRepo.findByAssignmentId(assignment.getId()).stream()
                            .map(AssignmentFile::getFileUrl)
                            .toList();
                    return AssignmentMapper.toDTO(assignment, fileUrls);
                })
                .toList();
    }

    public AssignmentResponseDTO getByCourseAndAssignment(UUID courseId, UUID assignmentId) {
        Assignment assignment = assignmentRepo.findByIdAndCourseId(assignmentId, courseId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        List<String> fileUrls = assignmentFileRepo.findByAssignmentId(assignmentId).stream()
                .map(AssignmentFile::getFileUrl)
                .toList();

        return AssignmentMapper.toDTO(assignment, fileUrls);
    }

    private String uploadViaContentService(MultipartFile file) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(contentServiceUrl, requestEntity, String.class);

        return response.getBody();
    }
}
