package com.profport.lms.assignments.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profport.lms.assignments.model.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, UUID> {

    List<Submission> findByAssignmentId(UUID assignmentId);

    List<Submission> findByStudentId(UUID studentId);

    Optional<Submission> findByAssignmentIdAndStudentId(UUID assignmentId, UUID studentId);

    List<Submission> findByStudentIdAndAssignment_CourseId(UUID studentId, UUID courseId);
}