package com.grade.gradeweb.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.grade.gradeweb.models.AppUser;
import com.grade.gradeweb.models.Secretary;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.repositories.AppUserRepository;
import com.grade.gradeweb.repositories.SecretaryRepository;
import com.grade.gradeweb.repositories.StudentRepository;

@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private SecretaryRepository secretaryRepository;
   

    public AppUser findByEmail(String email) {
    	 AppUser user =appUserRepository.findByEmail(email);
    	 return user;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return User.builder()
                .username(appUser.getEmail())
                .password(appUser.getPassword())
                .roles(appUser.getRole())
                .build();
    }

    public AppUser updateUserProfile(String email, String firstName, String lastName, String phone, String address) {
        AppUser user = appUserRepository.findByEmail(email);
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone(phone);
            user.setAddress(address);
            appUserRepository.save(user);
        }
        return user;
    }
    
    public AppUser getUser(String email) {
        return appUserRepository.findByEmail(email);
    }
    public void createUser(AppUser newUser, String role) {

        if ("STUDENT".equals(role)) {
            Student student = new Student(newUser.getId(), newUser.getFirstName(), newUser.getLastName(),
                    newUser.getEmail(), newUser.getPassword(), newUser.getAddress(), newUser.getPhone());
            studentRepository.save(student);
        } else if ("SECRETARY".equals(role)) {
            Secretary secretary = new Secretary(newUser.getId(), newUser.getFirstName(), newUser.getLastName(),
                    newUser.getEmail(), newUser.getPassword(), newUser.getAddress(), newUser.getPhone());
            secretaryRepository.save(secretary);
        }
   }
}