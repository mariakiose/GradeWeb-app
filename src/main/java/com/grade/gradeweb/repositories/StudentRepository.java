package com.grade.gradeweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grade.gradeweb.models.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	public Student findByEmail(String email);
    void deleteById(Long id);

}
