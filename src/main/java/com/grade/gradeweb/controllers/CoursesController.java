package com.grade.gradeweb.controllers;

import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CoursesController {
	
	@Autowired
	private  CourseService courseService;

   @GetMapping("/courses")
    public List<Course> getCourses() {
        return courseService.findAllCources();
    }

    @GetMapping("/courses/selected")
    public List<Course> getCoursesByIds(@RequestParam List<Long> courseIds) {
        return courseService.getCoursesByIds(courseIds);
    }

    @GetMapping("/courses/selected/{email}")
    public List<Course> getSelectedCourses(@PathVariable String email) {
        return courseService.getSelectedCourses(email);
    }
}