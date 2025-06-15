package com.profport.lms.assignments.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profport.lms.assignments.model.SubmissionFile;

public interface SubmissionFileRepository extends JpaRepository<SubmissionFile, UUID> {
    List<SubmissionFile> findBySubmissionId(UUID submissionId);
}
