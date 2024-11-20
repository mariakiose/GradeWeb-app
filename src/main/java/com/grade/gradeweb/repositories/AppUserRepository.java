package com.grade.gradeweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grade.gradeweb.models.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	public AppUser findByEmail(String email);
	boolean existsByEmail(String email); 
}
