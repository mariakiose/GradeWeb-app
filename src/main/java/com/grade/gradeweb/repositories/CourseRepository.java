package com.grade.gradeweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.grade.gradeweb.models.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
	//public Course findBycourseName(String courseName);

}
