package com.grade.gradeweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.models.Grade;
import com.grade.gradeweb.repositories.GradeRepository;
import com.grade.gradeweb.repositories.StudentRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class SecretaryController {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private GradeRepository gradeRepository;
   
    @GetMapping(value = { "/secretaries/index" })
	public String studentIndex() {
        return "secretaries/index"; 
    }
	
    @GetMapping("/students")
    public String showStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/student/{id}")
    public String showStudentCourses(@PathVariable Long id, Model model) {
        Optional<Student> studentOpt = studentRepository.findById(id);
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
    public String updateGrade(@PathVariable Long id, @RequestParam(required = false) Float gradeValue, @RequestParam Long studentId) {
        if (gradeValue != null) {
            Optional<Grade> gradeOpt = gradeRepository.findById(id);
            if (gradeOpt.isPresent()) {
                Grade grade = gradeOpt.get();
                grade.setGradeValue(gradeValue);
                gradeRepository.save(grade);
            }
        }
        return "redirect:/student/" + studentId;
    }
    }
