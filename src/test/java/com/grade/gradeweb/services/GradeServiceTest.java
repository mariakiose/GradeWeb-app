package com.grade.gradeweb.services;

import com.grade.gradeweb.models.Grade;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.repositories.GradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GradeServiceTest {

    @InjectMocks
    private GradeService gradeService;

    @Mock
    private GradeRepository gradeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindGradesByStudent_success() {
        // Arrange
        Student student = new Student();
        Grade grade1 = new Grade();
        Grade grade2 = new Grade();
        Grade grade3 = new Grade();
        grade1.setStudent(student);
        grade2.setStudent(student);
        grade3.setStudent(new Student()); // different student

        List<Grade> allGrades = Stream.of(grade1, grade2, grade3).collect(Collectors.toList());
        when(gradeRepository.findAll()).thenReturn(allGrades);

        // Act
        List<Grade> result = gradeService.findGradesByStudent(student);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(grade1));
        assertTrue(result.contains(grade2));
        assertFalse(result.contains(grade3));
    }

    @Test
    void testFindGradesByStudent_noGradesForStudent() {
        // Arrange
        Student student = new Student();
        Grade grade = new Grade();
        grade.setStudent(new Student()); // different student

        List<Grade> allGrades = List.of(grade);
        when(gradeRepository.findAll()).thenReturn(allGrades);

        // Act
        List<Grade> result = gradeService.findGradesByStudent(student);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindGradesByStudent_noGrades() {
        // Arrange
        Student student = new Student();
        when(gradeRepository.findAll()).thenReturn(List.of());

        // Act
        List<Grade> result = gradeService.findGradesByStudent(student);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
