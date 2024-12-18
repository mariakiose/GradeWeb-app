package com.grade.gradeweb.controllers;

import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CoursesControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CoursesController coursesController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(coursesController).build();
    }

    @Test
    public void testGetCourses() throws Exception {
        List<Course> courses = Arrays.asList(new Course(), new Course());

        when(courseService.findAllCources()).thenReturn(courses);

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) 
                .andExpect(jsonPath("$.length()").value(courses.size()));

        verify(courseService, times(1)).findAllCources();
    }

    @Test
    public void testGetCoursesByIds() throws Exception {
        List<Course> courses = Arrays.asList(new Course(), new Course());
        List<Long> courseIds = Arrays.asList(1L, 2L);

        when(courseService.getCoursesByIds(courseIds)).thenReturn(courses);

        mockMvc.perform(get("/courses/selected")
                        .param("courseIds", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) 
                .andExpect(jsonPath("$.length()").value(courses.size()));

        verify(courseService, times(1)).getCoursesByIds(courseIds);
    }

    @Test
    public void testGetSelectedCourses() throws Exception {
        List<Course> courses = Arrays.asList(new Course(), new Course());
        String email = "test@example.com";

        when(courseService.getSelectedCourses(email)).thenReturn(courses);

        mockMvc.perform(get("/courses/selected/{email}", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) 
                .andExpect(jsonPath("$.length()").value(courses.size()));

        verify(courseService, times(1)).getSelectedCourses(email);
    }
}
