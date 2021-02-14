package com.microservices.spring.cloud.users.ws.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/users/**").permitAll();
        //for h2-console to work with spring security ->> disable the frame-options header
        //The X-Frame-Options HTTP response header can be used to indicate whether or not a browser should be allowed to render a page in a <frame> , <iframe> , <embed> or <object> .
        // Sites can use this to avoid click-jacking attacks,
        // by ensuring that their content is not embedded into other sites.
        http.headers().frameOptions().disable();
    }
}
