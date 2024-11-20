package com.grade.gradeweb.services;


import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.models.Grade;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.repositories.GradeRepository;
import com.grade.gradeweb.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
 	private CourseService courseService;
    @Autowired
    private GradeRepository gradeRepository;
    
    public  Optional<Student> findById(Long id){
    	Optional<Student> studentOpt=studentRepository.findById(id);
    	return studentOpt;
    }

    public List<Student> findAllStudents(){
    	List<Student> students = studentRepository.findAll();
    	return students;
    }
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public void deleteStudent(Long studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            
            List<Grade> grades = gradeRepository.findByStudent(student);
            
            if (!grades.isEmpty()) {
                gradeRepository.deleteAll(grades);
            }
            
            studentRepository.delete(student);
        } else {
            throw new RuntimeException("Student not found");
        }
    }
    
    public void saveStudent(Student student,List<Long> courseIds) {
    	 if (student != null) {
	            for (Long courseId : courseIds) {
	                Course course = courseService.findById(courseId);
	                if (course != null && !student.getCourses().containsKey(course)) {
	                    Grade grade = new Grade();
	                    grade.setCourse(course);
	                    grade.setStudent(student);
	                    grade.setGradeValue(-1.0f); 
	                    gradeRepository.save(grade);
	                    student.getCourses().put(course, grade);
	                   // gradeRepository.save(grade);
	                }
	            }
	            studentRepository.save(student);
	        }
    }
  
    
}