package com.profport.lms.course.service;

import com.profport.lms.course.dto.CourseRequestDTO;
import com.profport.lms.course.dto.CourseResponseDTO;
import com.profport.lms.course.dto.InstructorResponseDTO;
import com.profport.lms.course.mapper.CourseMapper;
import com.profport.lms.course.model.User;
import com.profport.lms.course.repository.CourseRepository;
import com.profport.lms.course.model.Course;
import com.profport.lms.course.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseResponseDTO createCourse(CourseRequestDTO dto, UUID instructorId) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .instructor(instructor)
                .build();

        Course saved = courseRepository.save(course);
        return mapToResponse(saved);
    }

    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CourseResponseDTO getCourseById(UUID courseId) {
        return mapToResponse(courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found")));
    }

    public CourseResponseDTO updateCourse(UUID courseId, CourseRequestDTO dto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setCategory(dto.getCategory());

        Course updated = courseRepository.save(course);
        return mapToResponse(updated);
    }

    public void deleteCourse(UUID courseId) {
        courseRepository.deleteById(courseId);
    }

    public List<CourseResponseDTO> getCoursesForInstructor(UUID instructorId) {
        List<Course> courses = courseRepository.findByInstructor_Id(instructorId);
        return courses.stream()
                .map(CourseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    private CourseResponseDTO mapToResponse(Course course) {
        User instructor = course.getInstructor();

        InstructorResponseDTO instructorDTO = InstructorResponseDTO.builder()
                .id(instructor.getId())
                .name(instructor.getName())
                .email(instructor.getEmail())
                .build();

        return CourseResponseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .category(course.getCategory())
                .instructor(instructorDTO)
                .createdAt(course.getCreatedAt())
                .build();
    }
}