package com.example.demo.security;

import com.example.demo.jwt.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/productos/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/registrar").permitAll()
                        .requestMatchers("/user/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categoria/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/categoria/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categoria/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/categoria/**").hasRole("ADMIN")
                        .requestMatchers("/user/").hasAnyRole("ADMIN", "USER")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

