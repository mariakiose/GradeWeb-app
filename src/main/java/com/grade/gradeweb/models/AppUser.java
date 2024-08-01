package com.grade.gradeweb.models;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser implements UserDetails  {
	 @Id 
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id")
	 private Long id;
	
	 
	 private String firstName;
	 private String lastName;
	 
	 @Column(unique=true,nullable=false)
	 private String email;
	 
	 private String phone;
	 private String address;
	 private String password;
	 private Date createAt;
	 
	 
	 
	 private String role;
	 
	 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String adress) {
		this.address = adress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	  @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(getRole());
	        return Collections.singletonList(authority);
	    }

	    @Override
	    public String getUsername() {
	        return email;
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }
 }
