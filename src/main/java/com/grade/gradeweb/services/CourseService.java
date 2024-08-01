package com.grade.gradeweb.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.repositories.CourseRepository;
import com.grade.gradeweb.repositories.StudentRepository;


@Service
public class CourseService {
	@Autowired
	private CourseRepository courseRepository;
	

    @Autowired
    private StudentRepository studentRepository;
    
	 @GetMapping("/courses")
	public List<Course> getCources(){
		return courseRepository.findAll();
	}	
	
	 public List<Course> getCoursesByIds(List<Long> courseIds) {
	        return courseRepository.findAllById(courseIds);
	    }

	    public void saveSelectedCourses(List<Long> courseIds) {
	        List<Course> selectedCourses = getCoursesByIds(courseIds);
	    }
	    @Transactional(readOnly = true)
	    public List<Course> getSelectedCourses(String email) {
	        Student student = studentRepository.findByEmail(email);
	        if (student != null) {
	            return new ArrayList<>(student.getCourses().keySet());
	        }
	        return Collections.emptyList();
	    }

}
