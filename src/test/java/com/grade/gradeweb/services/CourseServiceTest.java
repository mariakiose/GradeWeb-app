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
    void testFindAllCources() {
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> courses = List.of(course1, course2);
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseService.findAllCources();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(course1));
        assertTrue(result.contains(course2));
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        
        Course course = new Course();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course result = courseService.findById(1L);

        assertNotNull(result);
        assertEquals(course, result);
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Course result = courseService.findById(1L);

        assertNull(result);
        verify(courseRepository, times(1)).findById(1L);
    }


    @Test
    void testSaveSelectedCourses() {
        List<Long> ids = List.of(1L, 2L);
        when(courseRepository.findAllById(ids)).thenReturn(new ArrayList<>());

        courseService.saveSelectedCourses(ids);

        verify(courseRepository, times(1)).findAllById(ids);
    }

    @Test
    void testGetSelectedCourses_StudentFound() {
        Course course = new Course();
        course.setCourseName("Networks");
        Student student = new Student();
        student.setCourses(Collections.singletonMap(course, null));
        when(studentRepository.findByEmail("test@example.com")).thenReturn(student);

        List<Course> result = courseService.getSelectedCourses("test@example.com");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Networks", result.get(0).getCourseName());
        verify(studentRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testGetSelectedCourses_StudentNotFound() {
        when(studentRepository.findByEmail("test@example.com")).thenReturn(null);

        List<Course> result = courseService.getSelectedCourses("test@example.com");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(studentRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testSaveCourse() {
        Course course = new Course();

        courseService.saveCourse(course);

        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testFindAllActiveCourses() {
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> activeCourses = List.of(course1, course2);
        when(courseRepository.findActiveCourses()).thenReturn(activeCourses);

        List<Course> result = courseService.findAllActiveCourses();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findActiveCourses();
    }

    @Test
    void testDisableCourse_CourseFound() {
        Course course = new Course();
        course.setActive(true);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        boolean result = courseService.disableCourse(1L);

        assertTrue(result);
        assertFalse(course.getActive());
        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testDisableCourse_CourseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = courseService.disableCourse(1L);

        assertFalse(result);
        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(0)).save(any(Course.class));
    }
}
