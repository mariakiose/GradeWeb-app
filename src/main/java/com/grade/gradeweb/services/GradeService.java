package com.grade.gradeweb.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grade.gradeweb.models.Grade;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.repositories.GradeRepository;

@Service
public class GradeService {
	 @Autowired
	    private GradeRepository gradeRepository;

	    public List<Grade> findGradesByStudent(Student student) {
	        return gradeRepository.findAll().stream()
	                .filter(g -> g.getStudent().equals(student))
	                .collect(Collectors.toList());
	    }
	    
	    public  Optional<Grade> findById(Long id){
	    	Optional<Grade> gradeOpt = gradeRepository.findById(id);
	    	return gradeOpt;
	    }
	    public void saveGrade(Optional<Grade> gradeOpt,Float gradeValue) {
	    	if (gradeOpt.isPresent()) {
                Grade grade = gradeOpt.get();
                grade.setGradeValue(gradeValue);
                gradeRepository.save(grade);
            }
	    }
}
