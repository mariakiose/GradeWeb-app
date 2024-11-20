package com.grade.gradeweb.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.models.Grade;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.services.CourseService;
import com.grade.gradeweb.services.StudentService;
import com.grade.gradeweb.util.GradeUtils;

@Controller
public class StudentController {
	 	
	 	
	 	@Autowired
	 	private CourseService courseService;
	 	
	 	@Autowired
	 	private StudentService studentService;

	 	
	 	@GetMapping(value = { "/students/index" })
		public String studentIndex() {
	        return "students/index"; 
	    }
		

	    @PreAuthorize("isAuthenticated()")
	    @GetMapping("/declaration")
	    public String showDeclareCoursesPage(Model model) {
	        List<Course> courses = courseService.findAllCources();
	        model.addAttribute("courses", courses);
	        return "declaration";
	    }

	    @PostMapping("/declaration")
	    public String declareCourses(@RequestParam List<Long> courseIds, Model model) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String email = authentication.getName();
	        Student student = studentService.findByEmail(email);
	        studentService.saveStudent(student,courseIds);
	      

	        List<Course> courses = courseService.findAllCources();
	        model.addAttribute("courses", courses);
	        return "declaration";
	    }

	    @PostMapping("/preview")
	    public String previewCourses(@RequestParam List<Long> courseIds, Model model) {
	        List<Course> selectedCourses = courseService.getCoursesByIds(courseIds);
	        model.addAttribute("selectedCourses", selectedCourses);
	        model.addAttribute("courseIds", courseIds);
	        return "preview";
	    }

	    @PostMapping("/save")
	    public String saveCourses(@RequestParam List<Long> courseIds, Model model) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String email = authentication.getName();
	        Student student = studentService.findByEmail(email);

	        studentService.saveStudent(student,courseIds);
	        model.addAttribute("success", true); 
	        List<Course> selectedCourses = courseService.getCoursesByIds(courseIds);
	        model.addAttribute("selectedCourses", selectedCourses);
	        return "preview";
	    }

	    @GetMapping("/grades")
	    @PreAuthorize("isAuthenticated()")
	    public String showMyCourses(Model model) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String email = authentication.getName();
	        Student student = studentService.findByEmail(email);

	        if (student != null) {
	            Map<Course, Grade> coursesWithGrades = student.getCourses();
	            model.addAttribute("coursesWithGrades", coursesWithGrades);
	            double averageGrade = GradeUtils.calculateAverageGrade(coursesWithGrades);
	            int passedCourses = GradeUtils.countPassedCourses(coursesWithGrades);

	            model.addAttribute("averageGrade", averageGrade);
	            model.addAttribute("passedCourses", passedCourses);
	        } else {
	            System.out.println("Student not found for email: " + email);
	        }

	        return "grades";
	    }


	
}