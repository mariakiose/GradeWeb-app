package com.grade.gradeweb.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.repositories.CourseRepository;
import com.grade.gradeweb.repositories.GradeRepository;
import com.grade.gradeweb.repositories.StudentRepository;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private GradeRepository gradeRepository;

    private Student mockStudent;

    @BeforeEach
    public void setup() {
        mockStudent = new Student();
        mockStudent.setEmail("test@example.com");
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testDeclareCourses() throws Exception {
        Course course1 = new Course();
        course1.setId(1L);
        Course course2 = new Course();
        course2.setId(2L);

        when(courseRepository.findById(1L)).thenReturn(java.util.Optional.of(course1));
        when(courseRepository.findById(2L)).thenReturn(java.util.Optional.of(course2));
        when(studentRepository.findByEmail("test@example.com")).thenReturn(mockStudent);

        MockHttpServletRequestBuilder request = post("/declaration")
            .param("courseIds", "1", "2")
            .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("declaration"));

        verify(gradeRepository, times(2)).save(any());
        verify(studentRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testPreviewCourses() throws Exception {
        Course course1 = new Course();
        course1.setId(1L);
        Course course2 = new Course();
        course2.setId(2L);

        when(courseRepository.findAllById(Arrays.asList(1L, 2L)))
                .thenReturn(Arrays.asList(course1, course2));

        MockHttpServletRequestBuilder request = post("/preview")
            .param("courseIds", "1", "2")
            .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("preview"))
                .andExpect(model().attributeExists("selectedCourses"))
                .andExpect(model().attributeExists("courseIds"));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testSaveCourses() throws Exception {
        Course course1 = new Course();
        course1.setId(1L);
        Course course2 = new Course();
        course2.setId(2L);

        when(courseRepository.findById(1L)).thenReturn(java.util.Optional.of(course1));
        when(courseRepository.findById(2L)).thenReturn(java.util.Optional.of(course2));
        when(studentRepository.findByEmail("test@example.com")).thenReturn(mockStudent);

        MockHttpServletRequestBuilder request = post("/save")
            .param("courseIds", "1", "2")
            .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("preview"))
                .andExpect(model().attributeExists("success"))
                .andExpect(model().attributeExists("selectedCourses"));

        verify(gradeRepository, times(2)).save(any());
        verify(studentRepository, times(1)).save(any());
    }
}
