package com.grade.gradeweb.services;

import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByEmail_success() {
        // Arrange
        String email = "test@example.com";
        Student student = new Student();
        student.setEmail(email);
        when(studentRepository.findByEmail(email)).thenReturn(student);

        // Act
        Student result = studentService.findByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }
    

    @Test
    void testFindByEmail_studentNotFound() {
        // Arrange
        String email = "test@example.com";
        when(studentRepository.findByEmail(email)).thenReturn(null);

        // Act
        Student result = studentService.findByEmail(email);

        // Assert
        assertNull(result);
    }
}
