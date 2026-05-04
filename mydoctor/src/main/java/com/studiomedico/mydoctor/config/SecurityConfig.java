package com.studiomedico.mydoctor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http.authorizeHttpRequests(auth -> auth

                    //accedo come voglio
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/actuator/**").permitAll()

                    //get sono liberi
                    .requestMatchers(HttpMethod.GET, "/api/patients/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/doctors/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/appointments/**").hasAnyRole("USER", "ADMIN")

                    //pero put post delete solo admin
                    .requestMatchers(HttpMethod.POST, "/api/patients/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/doctors/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/appointments/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/patients/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/doctors/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/appointments/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/patients/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/doctors/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/appointments/**").hasRole("ADMIN")

                    //se altre richieste mi autentico
                    .anyRequest().authenticated()
            );

            //popup
            http.httpBasic(Customizer.withDefaults());
            //csrf disabilitato
            http.csrf(csrf -> csrf.disable());

            return http.build();
        }


        //cripto password
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        //def ruoli e password
        @Bean
        public InMemoryUserDetailsManager userDetailsManager(PasswordEncoder encoder) {

            UserDetails admin = User.builder()
                    .username("admin").password(encoder.encode("gnegne000"))
                    .roles("ADMIN")
                    .build();

            UserDetails user = User.builder()
                    .username("paziente").password(encoder.encode("baubau321"))
                    .roles("USER")
                    .build();

            return new InMemoryUserDetailsManager(admin, user);
        }
    }