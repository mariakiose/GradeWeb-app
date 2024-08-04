package com.grade.gradeweb.services;

import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.repositories.CourseRepository;
import com.grade.gradeweb.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCourses_success() {
        // Arrange
        Course course1 = new Course("random_course1", null);
        Course course2 = new Course("random_course2", null);
        List<Course> courses = List.of(course1, course2);
        when(courseRepository.findAll()).thenReturn(courses);

        // Act
        List<Course> result = courseService.getCources();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(course1));
        assertTrue(result.contains(course2));
    }

    @Test
    void testGetCoursesByIds_success() {
        // Arrange
        Course course1 = new Course("random_course1", null);
        Course course2 = new Course("random_course2", null);
        List<Course> courses = List.of(course1, course2);
        List<Long> ids = List.of(1L, 2L);
        when(courseRepository.findAllById(ids)).thenReturn(courses);

        // Act
        List<Course> result = courseService.getCoursesByIds(ids);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(course1));
        assertTrue(result.contains(course2));
    }

    @Test
    void testSaveSelectedCourses_success() {
        // Arrange
        List<Long> ids = List.of(1L, 2L);

        // Act
        courseService.saveSelectedCourses(ids);

        // Assert
        verify(courseRepository, times(1)).findAllById(ids);
    }

    @Test
    void testGetSelectedCourses_studentFound() {
        // Arrange
        Course course = new Course("random_course1", null);
        Student student = new Student();
        student.setCourses(Collections.singletonMap(course, null)); // Add sample grade
        when(studentRepository.findByEmail("test@example.com")).thenReturn(student);

        // Act
        List<Course> result = courseService.getSelectedCourses("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("random_course1", result.get(0).getCourseName());
    }

    @Test
    void testGetSelectedCourses_studentNotFound() {
        // Arrange
        when(studentRepository.findByEmail("test@example.com")).thenReturn(null);

        // Act
        List<Course> result = courseService.getSelectedCourses("test@example.com");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
