package com.profport.lms.course.controller;

import com.profport.lms.course.dto.CourseRequestDTO;
import com.profport.lms.course.dto.CourseResponseDTO;
import com.profport.lms.course.dto.UserResponseDTO;
import com.profport.lms.course.model.User;
import com.profport.lms.course.security.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @PostMapping
    public CourseResponseDTO createCourse(
            @RequestBody CourseRequestDTO dto,
            @RequestHeader("Authorization") String authHeader) {

        UUID instructorId = jwtUtil.getUserIdFromToken(authHeader.replace("Bearer ", ""));
        return courseService.createCourse(dto, instructorId);
    }


    @GetMapping
    public List<CourseResponseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{courseId}")
    public CourseResponseDTO getCourse(@PathVariable UUID courseId) {
        return courseService.getCourseById(courseId);
    }

    @PutMapping("/{courseId}")
    public CourseResponseDTO updateCourse(@PathVariable UUID courseId, @RequestBody CourseRequestDTO dto) {
        return courseService.updateCourse(courseId, dto);
    }

    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable UUID courseId) {
        courseService.deleteCourse(courseId);
    }

    @PostMapping("/{courseId}/enroll")
    public void enrollStudent(
            @PathVariable UUID courseId,
            @RequestParam UUID studentId // or from JWT/auth later
    ) {
        enrollmentService.enrollStudent(courseId, studentId);
    }

    @GetMapping("/{courseId}/students")
    public List<UserResponseDTO> getEnrolledStudents(@PathVariable UUID courseId) {
        List<User> students = enrollmentService.getEnrolledStudents(courseId);
        return students.stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole()))
                .toList();
    }

    @GetMapping("/student/enrolled")
    public List<CourseResponseDTO> getCoursesForCurrentStudent(@RequestHeader("Authorization") String authHeader) {
        UUID userId = jwtUtil.getUserIdFromToken(authHeader.replace("Bearer ", ""));
        return enrollmentService.getCoursesForStudent(userId);
    }

    @GetMapping("/instructor/instructing")
    public List<CourseResponseDTO> getCoursesForCurrentInstructor(@RequestHeader("Authorization") String authHeader) {
        UUID userId = jwtUtil.getUserIdFromToken(authHeader.replace("Bearer ", ""));
        return courseService.getCoursesForInstructor(userId);
    }

}
