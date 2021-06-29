package com.loki.recipes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	log.info("Configuring HttpSecurity Parameters...");
    	httpSecurity.csrf().disable()
    				.cors().disable()
    				.authorizeRequests()
    				.antMatchers("/").permitAll()
    				.antMatchers("/api").permitAll()
    				.antMatchers("/api/recipes/*").permitAll()
    				.antMatchers("/error").permitAll()
    				//.antMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
    				//.antMatchers(HttpMethod.POST, "/api/register").permitAll()
    				.anyRequest().authenticated()
    				.and()
    				//.addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
    				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    
}
