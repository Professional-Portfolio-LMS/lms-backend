package com.profport.lms.assignments.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profport.lms.assignments.model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    List<Assignment> findByCourseId(UUID courseId);

    Optional<Assignment> findByIdAndCourseId(UUID id, UUID courseId);
}
