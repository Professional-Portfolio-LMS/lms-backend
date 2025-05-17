package com.profport.lms.course.service;

import com.profport.lms.course.model.Course;
import com.profport.lms.course.model.Enrollment;
import com.profport.lms.course.model.User;
import com.profport.lms.course.repository.CourseRepository;
import com.profport.lms.course.repository.EnrollmentRepository;
import com.profport.lms.course.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Transactional
    public void enrollStudent(UUID courseId, UUID studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (!student.getRole().equals("STUDENT")) {
            throw new RuntimeException("Only students can be enrolled");
        }

        // Avoid duplicate enrollments
        enrollmentRepository.findByStudentAndCourse(student, course)
                .ifPresent(e -> { throw new RuntimeException("Student already enrolled"); });

        Enrollment enrollment = Enrollment.builder()
                .course(course)
                .student(student)
                .build();

        enrollmentRepository.save(enrollment);
    }

    public List<User> getEnrolledStudents(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return enrollmentRepository.findByCourse(course)
                .stream()
                .map(Enrollment::getStudent)
                .toList();
    }
}
