package com.company.apppcmarket.security;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    @SneakyThrows
    public UserDetailsManager userDetailsManager(){
        User.UserBuilder user = User.builder();
        return new InMemoryUserDetailsManager(
                user.username("admin").password(passwordEncoder().encode("123")).roles("SUPER_ADMIN").build(),
                user.username("moderator").password(passwordEncoder().encode("123")).roles("MODERATOR").build(),
                user.username("operator").password(passwordEncoder().encode("123")).roles("OPERATOR").build()
        );
    }


    /**
     * comment: /api/product yo'li u-n ruxsatlar
     * (HttpMethod.GET,"/api/product","/api/product/**) => ixtiyoriy foydalanuvchi productlarni ko'ra oladi (bittasini va hammasini)
     * (HttpMethod.POST,"/api/product") => prodcut qo'sha oladi -> SUPER_ADMIN va MODERATOR
     * HttpMethod.PUT,"/api/product/*") => productni o'zgartira oladi -> SUPER_ADMIN va MODERATOR
     *
     *
     * comment: /api/customer yo'li u-n ruxsatlar
     * (HttpMethod.POST,"/api/customer") => ixtiyoriy foydalanuvchi customer bo'lishi mumkin
     *
     * comment: /api/order yo'li u-n ruxsatlar
     * (HttpMethod.GET,"/api/order","/api/order/**") => orderlarni barchasi va bittasi ko'ra oladi -> SUPER_ADMIN va OPERATOR
     * (HttpMethod.POST,"/api/order") => order yarata oladi -> SUPER_ADMIN va OPERATOR
     *
     *
     * comment : /api/address yo'li u-n ruxsatlar
     * (HttpMethod.POST, "/api/address") => foydalanuvchilar ro'yxatdan o'tishi uchun addressga ega bolishi kerak
     *
     *
     * ("/api/**").hasRole("SUPER_ADMIN") => SUPER_ADMIN  barcha huquqga ega
     */
    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize

                        .requestMatchers(HttpMethod.GET,"/api/product","/api/product/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/product").hasAnyRole("SUPER_ADMIN","MODERATOR")
                        .requestMatchers(HttpMethod.PUT,"/api/product/*").hasAnyRole("SUPER_ADMIN","MODERATOR")

                        .requestMatchers(HttpMethod.POST,"/api/customer").permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/order","/api/order/**").hasAnyRole("SUPER_ADMIN","OPERATOR")
                        .requestMatchers(HttpMethod.POST,"/api/order").hasAnyRole("SUPER_ADMIN","OPERATOR")

                        .requestMatchers(HttpMethod.POST, "/api/address").permitAll()

                        .requestMatchers("/api/**").hasRole("SUPER_ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
