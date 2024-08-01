package com.grade.gradeweb.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="secretaries")
public class Secretary extends AppUser{
	 
	
	 public Secretary() {
	        this.setRole("SECRETARY");

	 }
	 public Secretary(Long id, String firstName, String lastName, String email,String password, String address, String phone) {
		    this.setId(id);
		    this.setFirstName(firstName);
		    this.setLastName(lastName);
		    this.setEmail(email);
	        this.setPassword(password);

		    this.setAddress(address);
		    this.setPhone(phone);
		    this.setRole("SECRETARY"); 
		}

	  @OneToMany(mappedBy = "secretary")
	   private List<Course> courses = new ArrayList<>();




	public List<Course> getCourses() {
		return courses;
	}



	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

    
    


}
