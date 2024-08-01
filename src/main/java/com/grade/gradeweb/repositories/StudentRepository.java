package com.grade.gradeweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grade.gradeweb.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	public Student findByEmail(String email);
}
