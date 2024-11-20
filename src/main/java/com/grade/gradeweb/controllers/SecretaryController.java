package com.grade.gradeweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.models.Grade;
import com.grade.gradeweb.services.GradeService;
import com.grade.gradeweb.services.StudentService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class SecretaryController {

   
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private GradeService gradeService;
   
    @GetMapping(value = { "/secretaries/index" })
	public String studentIndex() {
        return "secretaries/index"; 
    }
	
    @GetMapping("/students")
    public String showStudents(Model model) {
        List<Student> students = studentService.findAllStudents();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/student/{id}")
    public String showStudentCourses(@PathVariable Long id, Model model) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            Map<Course, Grade> coursesWithGrades = student.getCourses();
            model.addAttribute("student", student);
            model.addAttribute("coursesWithGrades", coursesWithGrades);
        } else {
            model.addAttribute("errorMessage", "Student not found");
        }
        return "student_cources";
    }
    
    @PostMapping("/updateGrade/{id}")
    public String updateGrade(@PathVariable Long id, @RequestParam(required = false) Float gradeValue, @RequestParam Long studentId, Model model) {
        if (gradeValue != null) {
            if (gradeValue < 0 || gradeValue > 10) {
                model.addAttribute("errorMessage", "Grade must be between 0 and 10.");
                Optional<Student> studentOpt = studentService.findById(studentId);
                if (studentOpt.isPresent()) {
                    Student student = studentOpt.get();
                    Map<Course, Grade> coursesWithGrades = student.getCourses();
                    model.addAttribute("student", student);
                    model.addAttribute("coursesWithGrades", coursesWithGrades);
                }
                return "student_cources";
            }
            Optional<Grade> gradeOpt = gradeService.findById(id);
            gradeService.saveGrade(gradeOpt, gradeValue);
        }
        return "redirect:/student/" + studentId;
    }

    @PostMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable Long id) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isPresent()) {
            studentService.deleteStudent(id); 
        }

        return "redirect:/students"; 


    }
  }