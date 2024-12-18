package com.grade.gradeweb.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.grade.gradeweb.models.AppUser;
import com.grade.gradeweb.models.Secretary;
import com.grade.gradeweb.models.Student;
import com.grade.gradeweb.repositories.AppUserRepository;
import com.grade.gradeweb.repositories.SecretaryRepository;
import com.grade.gradeweb.repositories.StudentRepository;


public class AppUserServiceTest {

    @InjectMocks
    private AppUserService appUserService;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SecretaryRepository secretaryRepository;

    private AppUser mockUser;
    private String email;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        email = "test@example.com";
        mockUser = new AppUser();
        mockUser.setEmail(email);
        mockUser.setPassword("password");
        mockUser.setRole("STUDENT");
    }

    @Test
    public void testFindByEmail_UserExists() {
        when(appUserRepository.findByEmail(email)).thenReturn(mockUser);

        AppUser user = appUserService.findByEmail(email);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        verify(appUserRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testFindByEmail_UserDoesNotExist() {
        when(appUserRepository.findByEmail(email)).thenReturn(null);

        AppUser user = appUserService.findByEmail(email);

        assertNull(user);
        verify(appUserRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testLoadUserByUsername_UserExists() {
        when(appUserRepository.findByEmail(email)).thenReturn(mockUser);

        UserDetails userDetails = appUserService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        verify(appUserRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testLoadUserByUsername_UserDoesNotExist() {
        String email = "nonexistent@example.com";
        when(appUserRepository.findByEmail(email)).thenReturn(null);
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            appUserService.loadUserByUsername(email);
        });

        assertEquals("User not found with email: " + email, exception.getMessage());

        verify(appUserRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testUpdateUserProfile() {
        String firstName = "Mary";
        String lastName = "Kiose";
        String phone = "1234567890";
        String address = "123 Filotas";

        when(appUserRepository.findByEmail(email)).thenReturn(mockUser);
        when(appUserRepository.save(any(AppUser.class))).thenReturn(mockUser);

        AppUser updatedUser = appUserService.updateUserProfile(email, firstName, lastName, phone, address);

        assertNotNull(updatedUser);
        assertEquals(firstName, updatedUser.getFirstName());
        assertEquals(lastName, updatedUser.getLastName());
        assertEquals(phone, updatedUser.getPhone());
        assertEquals(address, updatedUser.getAddress());
        verify(appUserRepository, times(1)).save(any(AppUser.class));
    }

    @Test
    public void testCreateUser_Student() {
        String role = "STUDENT";
        
        AppUser newUser = new AppUser();
        newUser.setFirstName("Harry");
        newUser.setLastName("Potter");
        newUser.setEmail("harryPotter@example.com");
        newUser.setPassword("password");

        appUserService.createUser(newUser, role);

        verify(studentRepository, times(1)).save(any(Student.class));
        
        verify(secretaryRepository, times(0)).save(any(Secretary.class));
    }

    @Test
    public void testCreateUser_Secretary() {
        String role = "SECRETARY";
        
        AppUser newUser = new AppUser();
        newUser.setFirstName("Harry");
        newUser.setLastName("Styles");
        newUser.setEmail("HarryStyles@example.com");
        newUser.setPassword("password");

        appUserService.createUser(newUser, role);

        verify(secretaryRepository, times(1)).save(any(Secretary.class));
        
        verify(studentRepository, times(0)).save(any(Student.class));
    }
}
