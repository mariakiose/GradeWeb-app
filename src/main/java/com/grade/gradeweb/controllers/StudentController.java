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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.models.Grade;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.repositories.CourseRepository;
import com.grade.gradeweb.repositories.GradeRepository;
import com.grade.gradeweb.repositories.StudentRepository;
import com.grade.gradeweb.util.GradeUtils;

@Controller
public class StudentController {
	 @Autowired
	    private CourseRepository courseRepository;

	    @Autowired
	    private StudentRepository studentRepository;

	    @Autowired
	    private GradeRepository gradeRepository;
	    
		@GetMapping(value = { "/students/index" })
		public String studentIndex() {
	        return "students/index"; 
	    }
		

	    @PreAuthorize("isAuthenticated()")
	    @GetMapping("/declaration")
	    public String showDeclareCoursesPage(Model model) {
	        List<Course> courses = courseRepository.findAll();
	        model.addAttribute("courses", courses);
	        return "declaration";
	    }

	    @PostMapping("/declaration")
	    public String declareCourses(@RequestParam List<Long> courseIds, Model model) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String email = authentication.getName();
	        Student student = studentRepository.findByEmail(email);

	        if (student != null) {
	            for (Long courseId : courseIds) {
	                Course course = courseRepository.findById(courseId).orElse(null);
	                if (course != null && !student.getCourses().containsKey(course)) {
	                    Grade grade = new Grade();
	                    grade.setCourse(course);
	                    grade.setStudent(student);
	                    grade.setGradeValue(-1.0f); // Initial value for grade

	                    student.getCourses().put(course, grade);
	                    gradeRepository.save(grade);
	                }
	            }
	            studentRepository.save(student);
	        }

	        List<Course> courses = courseRepository.findAll();
	        model.addAttribute("courses", courses);
	        return "declaration";
	    }

	    @PostMapping("/preview")
	    public String previewCourses(@RequestParam List<Long> courseIds, Model model) {
	        List<Course> selectedCourses = courseRepository.findAllById(courseIds);
	        model.addAttribute("selectedCourses", selectedCourses);
	        model.addAttribute("courseIds", courseIds);
	        return "preview";
	    }

	    @PostMapping("/save")
	    public String saveCourses(@RequestParam List<Long> courseIds, Model model) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String email = authentication.getName();
	        Student student = studentRepository.findByEmail(email);

	        if (student != null) {
	            for (Long courseId : courseIds) {
	                Course course = courseRepository.findById(courseId).orElse(null);
	                if (course != null && !student.getCourses().containsKey(course)) {
	                    Grade grade = new Grade();
	                    grade.setCourse(course);
	                    grade.setStudent(student);
	                    grade.setGradeValue(-1.0f); // Initial value for grade

	                    student.getCourses().put(course, grade);
	                    gradeRepository.save(grade);
	                }
	            }
	            studentRepository.save(student);
	        }
	        model.addAttribute("success", true); 
	        List<Course> selectedCourses = courseRepository.findAllById(courseIds);
	        model.addAttribute("selectedCourses", selectedCourses);
	        return "preview";
	    }

	    @GetMapping("/grades")
	    @PreAuthorize("isAuthenticated()")
	    public String showMyCourses(Model model) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String email = authentication.getName();
	        Student student = studentRepository.findByEmail(email);

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
