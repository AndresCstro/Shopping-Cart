package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http
				//Acceso público
				.authorizeHttpRequests(auth -> auth
					.requestMatchers("/", "/index", "/index.html", "/login",
									"/error", "/css/**", "/js/**", "/images/**",  "/webjars/**")
						.permitAll()
					
					// GET público
					.requestMatchers(HttpMethod.GET, "/products")
					.permitAll()
					
					.requestMatchers("/cart", "/cart/**")
					.permitAll()
					
					.requestMatchers("/products/add",
									"/products/edit/**",
									"/products/save",
									"/products/delete/**")
						.hasRole("ADMIN")
					// Solo admin
					.requestMatchers("/admin", "/admin/**")
					.hasRole("ADMIN")
					// Las que no se correspondan con las anteriores deberán ser autenticados
					.anyRequest().authenticated()
					
					);
				
				
				
		return http.build();
	}
	
}
