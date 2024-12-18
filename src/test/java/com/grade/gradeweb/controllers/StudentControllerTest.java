package com.grade.gradeweb.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.models.Grade;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.services.CourseService;
import com.grade.gradeweb.services.StudentService;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private StudentService studentService;

    private Student mockStudent;

    @BeforeEach
    public void setup() {
        mockStudent = new Student();
        mockStudent.setEmail("test@example.com");
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "STUDENT")
    public void testStudentIndex() throws Exception {
        mockMvc.perform(get("/students/index"))
               .andExpect(status().isOk())
               .andExpect(view().name("students/index"));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "STUDENT")
    public void testShowDeclareCoursesPage() throws Exception {
        Course course1 = new Course();
        course1.setId(1L);
        Course course2 = new Course();
        course2.setId(2L);

        when(courseService.findAllActiveCourses()).thenReturn(Arrays.asList(course1, course2));

        mockMvc.perform(get("/declaration"))
               .andExpect(status().isOk())
               .andExpect(view().name("declaration"))
               .andExpect(model().attributeExists("active_courses"));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "STUDENT")
    public void testDeclareCourses() throws Exception {
        Course course1 = new Course();
        course1.setId(1L);
        Course course2 = new Course();
        course2.setId(2L);

        Student student = new Student();
        student.setEmail("test@example.com");

        when(courseService.findAllActiveCourses()).thenReturn(Arrays.asList(course1, course2));
        when(studentService.findByEmail("test@example.com")).thenReturn(student);

        mockMvc.perform(post("/declaration")
                .param("courseIds", "1", "2")
                .with(csrf()))
               .andExpect(status().isOk())
               .andExpect(view().name("declaration"))
               .andExpect(model().attributeExists("courses"));
        
        verify(studentService, times(1)).saveStudent(any(Student.class), eq(Arrays.asList(1L, 2L)));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "STUDENT")
    public void testPreviewCourses() throws Exception {
        Course course1 = new Course();
        course1.setId(1L);
        Course course2 = new Course();
        course2.setId(2L);

        when(courseService.getCoursesByIds(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(course1, course2));

        mockMvc.perform(post("/preview")
                .param("courseIds", "1", "2")
                .with(csrf()))
               .andExpect(status().isOk())
               .andExpect(view().name("preview"))
               .andExpect(model().attributeExists("selectedCourses"))
               .andExpect(model().attributeExists("courseIds"));
    }
   
    @Test
    @WithMockUser(username = "test@example.com", roles = "STUDENT")
    public void testSaveCourses() throws Exception {
        Course course1 = new Course();
        course1.setId(1L);
        Course course2 = new Course();
        course2.setId(2L);

        when(courseService.findById(1L)).thenAnswer(invocation -> Optional.of(course1));
        when(courseService.findById(2L)).thenAnswer(invocation -> Optional.of(course2));

        when(studentService.findByEmail("test@example.com")).thenReturn(mockStudent);

        mockMvc.perform(post("/save")
                .param("courseIds", "1", "2")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("preview"))
                .andExpect(model().attributeExists("success"))
                .andExpect(model().attributeExists("selectedCourses"));

        verify(studentService, times(1)).saveStudent(any(Student.class), eq(Arrays.asList(1L, 2L)));
    }


    @Test
    @WithMockUser(username = "test@example.com", roles = "STUDENT")
    public void testShowMyCourses() throws Exception {
        Course course1 = new Course();
        course1.setId(1L);
        Grade grade1 = new Grade();
        grade1.setGradeValue(9.0f);
        Map<Course, Grade> coursesWithGrades = Map.of(course1, grade1);

        when(studentService.findByEmail("test@example.com")).thenReturn(mockStudent);
        mockStudent.setCourses(coursesWithGrades);

        mockMvc.perform(get("/grades"))
               .andExpect(status().isOk())
               .andExpect(view().name("grades"))
               .andExpect(model().attributeExists("coursesWithGrades"))
               .andExpect(model().attributeExists("averageGrade"))
               .andExpect(model().attributeExists("passedCourses"));
    }
}
