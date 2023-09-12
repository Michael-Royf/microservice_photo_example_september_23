package com.michael.usersService.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurity {


    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                 .requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
             //  .requestMatchers(HttpMethod.POST, "/api/v1/users/**").access(new WebExpressionAuthorizationManager("hasIpAddress('192.168.56.1')"))
               .requestMatchers(HttpMethod.POST, "/api/v1/users/**").access(new WebExpressionAuthorizationManager("hasIpAddress('127.0.0.0/16')"))
            //    .requestMatchers(HttpMethod.POST, "/api/v1/users/**").access(new WebExpressionAuthorizationManager("hasIpAddress('0.0.0.4')"))
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();

        return http.build();
    }

}
