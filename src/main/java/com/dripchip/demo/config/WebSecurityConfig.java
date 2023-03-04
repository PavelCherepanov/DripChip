package com.dripchip.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * @author Pavel
 * @version 1.0
 * @date 11.02.2023 22:46
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private DataSource dataSource;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/accounts/**", "/accounts/registration").permitAll().anyRequest().permitAll()
                );
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/accounts/registration").anonymous()
//                        .anyRequest().anonymous().and()
//                );
//        http.authorizeHttpRequests()
//                .requestMatchers("/api/v1/accounts", "/api/v1/accounts/registration")
//                .permitAll();
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select firstName, lastName, email, password from accounts where email=?");
//                .authoritiesByUsernameQuery("select u.username, ur.roles from  where u.username=?");
    }

}
