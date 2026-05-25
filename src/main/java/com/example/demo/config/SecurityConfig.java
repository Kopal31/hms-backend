
package com.example.demo.config;

import com.example.demo.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("*"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                return config;
            }))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/", "/auth/**").permitAll()
                .requestMatchers("/api/patients/me").hasAnyAuthority("ROLE_PATIENT", "ROLE_ADMIN", "ROLE_STAFF")
                .requestMatchers("/api/patients/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")
                .requestMatchers(HttpMethod.GET, "/api/doctors").hasAnyAuthority("ROLE_PATIENT", "ROLE_ADMIN", "ROLE_STAFF")
                .requestMatchers("/api/doctors/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/appointments/my", "/api/appointments/my/**").hasAnyAuthority("ROLE_PATIENT", "ROLE_ADMIN", "ROLE_STAFF")
                .requestMatchers("/api/prescriptions/my").hasAnyAuthority("ROLE_PATIENT", "ROLE_ADMIN")
                .requestMatchers("/api/labreports/my").hasAnyAuthority("ROLE_PATIENT", "ROLE_ADMIN")
                .requestMatchers("/api/billings/my", "/api/payments/my").hasAnyAuthority("ROLE_PATIENT", "ROLE_ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
