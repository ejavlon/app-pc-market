package com.company.apppcmarket.security;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @SneakyThrows
    public UserDetailsManager userDetailsManager(){
        User.UserBuilder user = User.builder();
        return new InMemoryUserDetailsManager(
                user.username("admin").password(passwordEncoder.encode("123")).roles("SUPER_ADMIN").build(),
                user.username("moderator").password(passwordEncoder.encode("123")).roles("MODERATOR").build(),
                user.username("operator").password(passwordEncoder.encode("123")).roles("OPERATOR").build()
        );
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET,"/api/product/","/api/product/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/order/**").hasAnyRole("SUPER_ADMIN","OPERATOR")
                        .requestMatchers(HttpMethod.POST,"/api/order").hasAnyRole("SUPER_ADMIN","OPERATOR")
                        .requestMatchers(HttpMethod.GET,"/api/product/**").hasAnyRole("SUPER_ADMIN","MODERATOR")
                        .requestMatchers(HttpMethod.POST,"/api/product").hasAnyRole("SUPER_ADMIN","MODERATOR")
                        .requestMatchers(HttpMethod.PUT,"/api/product/*").hasAnyRole("SUPER_ADMIN","MODERATOR")
                        .requestMatchers("/api/**").hasRole("SUPER_ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
