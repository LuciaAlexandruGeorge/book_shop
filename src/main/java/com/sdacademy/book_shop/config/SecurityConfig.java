package com.sdacademy.book_shop.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().ignoringAntMatchers("/api/**")
                .and()
                .authorizeRequests()
                .antMatchers(GET, "/api/books").hasAnyRole("ADMIN", "BOOKS")
                .antMatchers(POST, "/api/books").authenticated()
                .antMatchers("/api/users/").hasRole("USER_ADMIN")
                .anyRequest().permitAll()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .and()
                .logout();

    }
}
