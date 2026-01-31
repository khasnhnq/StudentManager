package com.example.schoolmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // PUBLIC
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()

                // USER + ADMIN ĐƯỢC XEM
                .requestMatchers(
                    "/students",
                    "/students/",
                    "/students/search",
                    "/students/{id}"
                ).hasAnyRole("USER", "ADMIN")

                // ADMIN MỚI ĐƯỢC SỬA / XÓA / ADD
                .requestMatchers(
                    "/students/add",
                    "/students/save",
                    "/students/edit/**",
                    "/students/delete/**"
                ).hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/students", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        return new InMemoryUserDetailsManager(
            User.withUsername("admin").password("{noop}123").roles("ADMIN").build(),
            User.withUsername("user").password("{noop}123").roles("USER").build()
        );
    }
}

