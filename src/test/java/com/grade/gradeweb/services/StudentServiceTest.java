package com.grade.gradeweb.services;

import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.models.Grade;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.repositories.GradeRepository;
import com.grade.gradeweb.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseService courseService;
    @Mock
    private GradeRepository gradeRepository;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
   
    @Test
    void testDeleteStudent_success() {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);

        List<Grade> grades = Arrays.asList(new Grade());

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(gradeRepository.findByStudent(student)).thenReturn(grades);

        studentService.deleteStudent(studentId);

        verify(gradeRepository, times(1)).deleteAll(grades);
        verify(studentRepository, times(1)).delete(student);
    }


    @Test
    void testSaveStudent_success() {
        Student student = new Student();
        student.setId(1L);
        List<Long> courseIds = Arrays.asList(1L, 2L);

        Course course1 = new Course();
        course1.setId(1L);
        Course course2 = new Course();
        course2.setId(2L);

        when(courseService.findById(1L)).thenReturn(course1);
        when(courseService.findById(2L)).thenReturn(course2);

        studentService.saveStudent(student, courseIds);

        verify(gradeRepository, times(2)).save(any(Grade.class));
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testFindById_studentExists() {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.findById(studentId);

        assertTrue(result.isPresent());
        assertEquals(studentId, result.get().getId());
    }

    @Test
    void testFindById_studentNotFound() {
        Long studentId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        Optional<Student> result = studentService.findById(studentId);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAllStudents() {
        List<Student> students = Arrays.asList(new Student(), new Student());

        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.findAllStudents();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
    @Test
    void testFindByEmail_success() {
        String email = "test@example.com";
        Student student = new Student();
        student.setEmail(email);
        when(studentRepository.findByEmail(email)).thenReturn(student);

        Student result = studentService.findByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }
    

    @Test
    void testFindByEmail_studentNotFound() {
        String email = "test@example.com";
        when(studentRepository.findByEmail(email)).thenReturn(null);

        Student result = studentService.findByEmail(email);

        assertNull(result);
    }
}
