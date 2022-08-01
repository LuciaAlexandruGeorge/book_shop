package com.sdacademy.book_shop.config;

import com.sdacademy.book_shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
    // permiti accesul la resurse, definesti form login
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/", "/index", "/menu/**", "/register", "/login").permitAll()
                .antMatchers("/favicon.ico", "/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/admin/**", "/products/**", "/product-categories/**").hasAnyRole("ADMIN")
                .antMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/shoppingCart/checkout").authenticated()
                .antMatchers("/shoppingCart/**").permitAll()
                .anyRequest().authenticated();
        // custom form (nice to have)
        http.formLogin().loginPage("/login").loginProcessingUrl("/login");
        // custom logout
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll();
    }

    // cu web.ignoring pathurile de mai jos nu mai trec prin spring security context
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/webjars/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
