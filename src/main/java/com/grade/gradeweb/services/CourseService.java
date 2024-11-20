package com.grade.gradeweb.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Course> findAllCources() {
        return courseRepository.findAll(); 
    }
    public Course findById(Long courseId) {
    	Course course = courseRepository.findById(courseId).orElse(null);
    	return course;
    	
    }

    public List<Course> getCoursesByIds(List<Long> courseIds) {
        return courseRepository.findAllById(courseIds); 
    }

    public void saveSelectedCourses(List<Long> courseIds) {
        List<Course> selectedCourses = getCoursesByIds(courseIds);
        
    }
    
    public  List<Course> findAllCoursesById( List<Long> courseIds){
    	List<Course> selectedCourses = courseRepository.findAllById(courseIds);
    	return selectedCourses;
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