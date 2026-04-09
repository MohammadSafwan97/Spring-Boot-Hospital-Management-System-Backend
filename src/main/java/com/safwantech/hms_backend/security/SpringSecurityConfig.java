package com.safwantech.hms_backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SpringSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity

                // Enable CORS
                .cors(cors -> {})

                // Disable CSRF for APIs
                .csrf(csrf -> csrf.disable())

                // Stateless JWT authentication
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth ->
                        auth

                                // Public endpoints
                                .requestMatchers("/api/auth/login", "/api/auth/signup").permitAll()
                                .requestMatchers("/api/platform/**").hasAuthority("SUPER_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/clinics/current").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/clinics/current").hasAnyAuthority("ADMIN", "DOCTOR", "RECEPTIONIST")
                                .requestMatchers(HttpMethod.POST, "/api/users").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority("ADMIN")

                                // Everything else secured
                                .anyRequest().authenticated()
                )

                // JWT filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
