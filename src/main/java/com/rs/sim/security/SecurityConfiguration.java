package com.rs.sim.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration {

  @Autowired private AwsCognitoJwtAuthFilter awsCognitoJwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests(
            expressionInterceptUrlRegistry ->
                expressionInterceptUrlRegistry
                    .anyRequest()
                    .authenticated()
                    .and()
                    .addFilterAfter(
                        awsCognitoJwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class))
        .oauth2ResourceServer()
        .jwt();
    return http.build();
  }
}
