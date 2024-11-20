package com.grade.gradeweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/contact").permitAll()
                .requestMatchers("/profile").authenticated()
                .requestMatchers("/register/**").permitAll()
                .requestMatchers("/grades").authenticated()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/students").authenticated()
                .requestMatchers("/student_cources").authenticated()
                .requestMatchers("//students/index").hasAuthority("STUDENT")
                .requestMatchers("//secretaries/index").hasAuthority("SECRETARY")

                
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                //.loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler())
                .permitAll()
            )
            .logout(config -> config
                .logoutSuccessUrl("/index")
                .permitAll()
            )
            
            .build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
