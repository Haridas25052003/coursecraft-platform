package com.vibrantminds.vibrantminds.config;

import com.vibrantminds.vibrantminds.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth

                //  Public URLs - anyone can access
                .requestMatchers(
                        "/",
                        "/index.html",
                        "/jobs.html",
                        "/courses.html",
                        "/contact.html",
                        "/about.html",
                        "/login.html",
                        "/register.html",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/admin/login.html"
                ).permitAll()

                //  Public APIs - anyone can access
                .requestMatchers(
                        "/api/admin/login",
                        "/api/admin/register",
                        "/api/candidate/register",
                        "/api/candidate/login",
                        "/api/jobs",
                        "/api/jobs/**",
                        "/api/courses",
                        "/api/courses/**",
                        "/api/testimonials",
                        "/api/contact",
                        "/api/colleges/**",
                        "/api/companies/**",
                        "/api/enrollments/**"
                ).permitAll()

                //  Admin only APIs
                .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                //  Candidate only APIs
                .requestMatchers("/api/candidate/**")
                        .hasRole("CANDIDATE")

                //  Everything else needs authentication
                .anyRequest().authenticated()
            )
            .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}