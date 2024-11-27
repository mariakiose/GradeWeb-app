package com.grade.gradeweb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.grade.gradeweb.models.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
	@Query("SELECT c FROM Course c WHERE c.active = true")
    List<Course> findActiveCourses();
}
