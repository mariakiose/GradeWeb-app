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
import com.grade.gradeweb.services.GradeService;
import com.grade.gradeweb.services.StudentService;

import java.util.*;

public class SecretaryControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private GradeService gradeService;

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

        when(studentService.findAllStudents()).thenReturn(students);

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

        when(studentService.findById(studentId)).thenReturn(Optional.of(student));

        String viewName = secretaryController.showStudentCourses(studentId, model);

        assertEquals("student_cources", viewName);
        verify(model).addAttribute("student", student);
        verify(model).addAttribute("coursesWithGrades", coursesWithGrades);
    }

    @Test
    public void testShowStudentCourses_StudentNotFound() {
        Long studentId = 1L;

        when(studentService.findById(studentId)).thenReturn(Optional.empty());

        String viewName = secretaryController.showStudentCourses(studentId, model);

        assertEquals("student_cources", viewName);
        verify(model).addAttribute("errorMessage", "Student not found");
    }

    @Test
    public void testUpdateGrade() {
        Long gradeId = 1L;
        Float gradeValue = 8.0F;  // Valid grade
        Long studentId = 2L;

        Grade grade = new Grade();
        when(gradeService.findById(gradeId)).thenReturn(Optional.of(grade));

        String viewName = secretaryController.updateGrade(gradeId, gradeValue, studentId, model);

        assertEquals("redirect:/student/" + studentId, viewName);
        verify(gradeService).saveGrade(Optional.of(grade), gradeValue);
    }

    @Test
    public void testUpdateGrade_NoGradeValue() {
        Long gradeId = 1L;
        Long studentId = 2L;

        String viewName = secretaryController.updateGrade(gradeId, null, studentId, model);

        assertEquals("redirect:/student/" + studentId, viewName);
        verify(gradeService, never()).findById(gradeId);
        verify(gradeService, never()).saveGrade(any(), any());
    }

    @Test
    public void testUpdateGrade_InvalidGradeValue() {
        Long gradeId = 1L;
        Float invalidGradeValue = 15.0F;  // Invalid grade
        Long studentId = 2L;

        String viewName = secretaryController.updateGrade(gradeId, invalidGradeValue, studentId, model);

        assertEquals("student_cources", viewName);
        verify(model).addAttribute("errorMessage", "Grade must be between 0 and 10.");
        verify(gradeService, never()).saveGrade(any(), any());
    }

    @Test
    public void testUpdateGrade_GradeNotFound() {
        Long gradeId = 1L;
        Float gradeValue = 8.0F;  // Valid grade
        Long studentId = 2L;

        when(gradeService.findById(gradeId)).thenReturn(Optional.empty());

        String viewName = secretaryController.updateGrade(gradeId, gradeValue, studentId, model);

        assertEquals("redirect:/student/" + studentId, viewName);
        verify(model).addAttribute("errorMessage", "Grade not found.");
    }
}
