package com.profport.lms.course.repository;

import com.profport.lms.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    // Custom queries can go here
    List<Course> findByInstructor_Id(UUID instructorId);
}