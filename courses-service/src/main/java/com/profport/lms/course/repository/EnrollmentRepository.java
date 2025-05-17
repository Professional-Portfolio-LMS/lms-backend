package com.profport.lms.course.repository;

import com.profport.lms.course.model.Course;
import com.profport.lms.course.model.Enrollment;
import com.profport.lms.course.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    Optional<Enrollment> findByStudentAndCourse(User student, Course course);
    List<Enrollment> findByCourse(Course course);
}
