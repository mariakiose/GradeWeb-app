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
import java.util.Optional;
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
        Student student = new Student();
        Grade grade1 = new Grade();
        Grade grade2 = new Grade();
        Grade grade3 = new Grade();
        grade1.setStudent(student);
        grade2.setStudent(student);
        grade3.setStudent(new Student()); 

        List<Grade> allGrades = Stream.of(grade1, grade2, grade3).collect(Collectors.toList());
        when(gradeRepository.findAll()).thenReturn(allGrades);

        List<Grade> result = gradeService.findGradesByStudent(student);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(grade1));
        assertTrue(result.contains(grade2));
        assertFalse(result.contains(grade3));
    }

    @Test
    void testFindGradesByStudent_noGradesForStudent() {
        Student student = new Student();
        Grade grade = new Grade();
        grade.setStudent(new Student()); 

        List<Grade> allGrades = List.of(grade);
        when(gradeRepository.findAll()).thenReturn(allGrades);

        List<Grade> result = gradeService.findGradesByStudent(student);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @Test
    void testFindById_success() {
        Grade grade = new Grade();
        grade.setId(1L);
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));

        Optional<Grade> result = gradeService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(grade, result.get());
    }

    @Test
    void testFindById_notFound() {
        when(gradeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Grade> result = gradeService.findById(1L);

        assertTrue(result.isEmpty());
    }

  
    @Test
    void testSaveGrade_success() {
        Grade grade = new Grade();
        grade.setId(1L);
        grade.setGradeValue(0.0f);
        Optional<Grade> gradeOpt = Optional.of(grade);
        Float gradeValue = 8.0f;

        gradeService.saveGrade(gradeOpt, gradeValue);

        assertEquals(gradeValue, grade.getGradeValue(), 0.0001, "The grade value should be updated to the new value.");
        verify(gradeRepository, times(1)).save(grade); 
    }


}
