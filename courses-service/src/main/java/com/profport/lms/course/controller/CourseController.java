package com.profport.lms.course.controller;

import com.profport.lms.course.dto.CourseRequestDTO;
import com.profport.lms.course.dto.CourseResponseDTO;
import com.profport.lms.course.dto.UserResponseDTO;
import com.profport.lms.course.model.User;
import com.profport.lms.course.service.CourseService;
import com.profport.lms.course.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @PostMapping
    public CourseResponseDTO createCourse(@RequestBody CourseRequestDTO dto) {
        return courseService.createCourse(dto);
    }

    @GetMapping
    public List<CourseResponseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseResponseDTO getCourse(@PathVariable UUID id) {
        return courseService.getCourseById(id);
    }

    @PutMapping("/{id}")
    public CourseResponseDTO updateCourse(@PathVariable UUID id, @RequestBody CourseRequestDTO dto) {
        return courseService.updateCourse(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
    }

    @PostMapping("/{id}/enroll")
    public void enrollStudent(
            @PathVariable UUID id,
            @RequestParam UUID studentId  // or from JWT/auth later
    ) {
        enrollmentService.enrollStudent(id, studentId);
    }

    @GetMapping("/{id}/students")
    public List<UserResponseDTO> getEnrolledStudents(@PathVariable UUID id) {
        List<User> students = enrollmentService.getEnrolledStudents(id);
        return students.stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole()))
                .toList();
    }
}
