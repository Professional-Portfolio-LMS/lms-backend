package com.profport.lms.course.repository;

import com.profport.lms.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    // Custom queries can go here
}