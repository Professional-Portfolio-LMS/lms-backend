package com.profport.lms.assignments.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
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
import com.profport.lms.assignments.util.MultipartInputStreamFileResource;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepo;
    private final SubmissionFileRepository submissionFileRepo;
    private final AssignmentRepository assignmentRepo;
    private final RestTemplate restTemplate;
    private final String contentServiceUrl = "http://gateway:8080/content/upload";

    public SubmissionResponseDTO submit(SubmissionRequestDTO dto, List<MultipartFile> files) throws IOException {
        Assignment assignment = assignmentRepo.findById(dto.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        List<MultipartFile> safeFiles = files != null ? files : List.of();

        Submission submission = submissionRepo.save(Submission.builder()
                .assignment(assignment)
                .studentId(dto.getStudentId())
                .comment(dto.getComment())
                .build());

        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile file : safeFiles) {
            String fileUrl = uploadViaContentService(file);
            submissionFileRepo.save(SubmissionFile.builder()
                    .submission(submission)
                    .fileUrl(fileUrl)
                    .build());
            fileUrls.add(fileUrl);
        }

        return SubmissionMapper.toDTO(submission, fileUrls);
    }

    public List<SubmissionResponseDTO> getSubmissions(UUID courseId, UUID assignmentId) {
        // Ensure assignment exists and belongs to the course
        Assignment assignment = assignmentRepo.findByIdAndCourseId(assignmentId, courseId)
            .orElseThrow(() -> new RuntimeException("Assignment not found for given course"));

        List<Submission> submissions = submissionRepo.findByAssignmentId(assignmentId);

        return submissions.stream()
            .map(sub -> {
                List<String> fileUrls = submissionFileRepo.findBySubmissionId(sub.getId()).stream()
                    .map(SubmissionFile::getFileUrl)
                    .toList();
                return SubmissionMapper.toDTO(sub, fileUrls);
            })
            .toList();
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
