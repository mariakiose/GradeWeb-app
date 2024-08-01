package com.grade.gradeweb.repositories;

import com.grade.gradeweb.models.Grade;
import com.grade.gradeweb.models.Student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
	public  List<Grade> findByStudent(Student student);


}
