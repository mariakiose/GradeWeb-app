package com.grade.gradeweb.services;

import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.models.Grade;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

  
    
}
