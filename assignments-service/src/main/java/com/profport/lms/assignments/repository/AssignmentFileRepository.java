package com.profport.lms.assignments.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profport.lms.assignments.model.AssignmentFile;

public interface AssignmentFileRepository extends JpaRepository<AssignmentFile, UUID> {
    List<AssignmentFile> findByAssignmentId(UUID assignmentId);
}
