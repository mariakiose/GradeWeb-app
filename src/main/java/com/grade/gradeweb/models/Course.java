package com.grade.gradeweb.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String courseName;
    @ManyToOne
    private Secretary secretary;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();
   
    public Course() {
    }
    public void removeStudent(Student student) {
        if (this.students.contains(student)) {
            this.students.remove(student);  
        }
    }
    
    public Course(Long id, String courseName) {
        this.id = id;
        this.courseName = courseName;
    }
    public Course( String courseName,Secretary secretary) {
        this.secretary = secretary;
        this.courseName = courseName;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Secretary getSecretary() {
		return secretary;
	}

	public void setSecretary(Secretary secretary) {
		this.secretary = secretary;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public void addStudents(Student students) {
		this.students.add(students);
	}
	
	public void removeStudents(Student students) {
		this.students.remove(students);
	}
	
	
    
}