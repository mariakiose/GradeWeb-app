package com.grade.gradeweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grade.gradeweb.models.Secretary;

public interface SecretaryRepository extends JpaRepository<Secretary,Long> {
	//public Secretary findByEmail(String email);

}
