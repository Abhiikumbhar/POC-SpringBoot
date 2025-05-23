package com.learning.POC.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails jhon = User.builder()
                .username("jhon")
                .password("{noop}test123")
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return  new InMemoryUserDetailsManager(jhon, mary, susan);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/v1/product").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/product/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/product").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/product/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/product/**").hasRole("ADMIN")
                        //uncomment this to access swagger-ui
//                        .requestMatchers(HttpMethod.GET,"/**").hasRole("ADMIN")
        );

        //using HTTP Basic authentication here
        http.httpBasic(Customizer.withDefaults());

        //disable cross site req forgery (csrf)
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
