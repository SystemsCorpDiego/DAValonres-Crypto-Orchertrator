package com.davalores.crypto.orchestrator.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebSecurityConfiguration {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	.authorizeHttpRequests (
        		auth -> auth.anyRequest().permitAll()                
                //auth.anyRequest().authenticated()
        		)

            .csrf(csrf -> csrf.disable())        
            //.httpBasic(Customizer.withDefaults())
            ;
        
        return http.build();
    }

}
