package com.grade.gradeweb.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.models.Grade;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.repositories.GradeRepository;
import com.grade.gradeweb.repositories.StudentRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SecretaryControllerTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private Model model;

    @InjectMocks
    private SecretaryController secretaryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStudentIndex() {
        String viewName = secretaryController.studentIndex();
        assertEquals("secretaries/index", viewName);
    }

    @Test
    public void testShowStudents() {
        // Test data
        List<Student> students = Arrays.asList(new Student(), new Student());

        when(studentRepository.findAll()).thenReturn(students);

        // Execute the method
        String viewName = secretaryController.showStudents(model);

        // Verify the results
        assertEquals("students", viewName);
        verify(model).addAttribute("students", students);
    }

    @Test
    public void testShowStudentCourses() {
        Long studentId = 1L;
        Student student = new Student();
        Map<Course, Grade> coursesWithGrades = new HashMap<>();
        student.setCourses(coursesWithGrades);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        String viewName = secretaryController.showStudentCourses(studentId, model);

        assertEquals("student_cources", viewName);
        verify(model).addAttribute("student", student);
        verify(model).addAttribute("coursesWithGrades", coursesWithGrades);
    }

    @Test
    public void testShowStudentCourses_StudentNotFound() {
        Long studentId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        String viewName = secretaryController.showStudentCourses(studentId, model);

        assertEquals("student_cources", viewName);
        verify(model).addAttribute("errorMessage", "Student not found");
    }

    @Test
    public void testUpdateGrade() {
        Long gradeId = 1L;
        Float gradeValue = 85.0F;
        Long studentId = 2L;

        Grade grade = new Grade();
        when(gradeRepository.findById(gradeId)).thenReturn(Optional.of(grade));

        String viewName = secretaryController.updateGrade(gradeId, gradeValue, studentId);

        assertEquals("redirect:/student/" + studentId, viewName);
        assertEquals(gradeValue, grade.getGradeValue(), 0.01); // Using delta for floating-point comparison
        verify(gradeRepository).save(grade);
    }

    @Test
    public void testUpdateGrade_NoGradeValue() {
        Long gradeId = 1L;
        Long studentId = 2L;

        String viewName = secretaryController.updateGrade(gradeId, null, studentId);

        assertEquals("redirect:/student/" + studentId, viewName);
        verify(gradeRepository, never()).findById(gradeId);
        verify(gradeRepository, never()).save(any(Grade.class));
    }
}
