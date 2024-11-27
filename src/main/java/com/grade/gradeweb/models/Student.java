package com.grade.gradeweb.models;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Table;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "students")
public class Student extends AppUser {
   

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_id"))
    @MapKeyJoinColumn(name = "course_id")
    @Column(name = "grade_id", nullable = true)
    private Map<Course, Grade> courses = new HashMap<>();

    public Student(Long id, String firstName, String lastName, String email,String password, String address, String phone) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setAddress(address);
        this.setPhone(phone);
        this.setRole("STUDENT"); 
    }

    public Student() {
        this.setRole("STUDENT");
    }

 
    public Map<Course, Grade> getCourses() {
        return courses;
    }

    public void setCourses(Map<Course, Grade> courses) {
        this.courses = courses;
    }
}