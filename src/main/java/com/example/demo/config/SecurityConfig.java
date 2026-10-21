package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
						
						.requestMatchers("/h2-console", "/h2-console/**")
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
						
				)
				// Gestión Login --> devolver login.html
				.formLogin(form -> form
						.loginPage("/login")
						// Importante "false" para que Spring respete la página que el user 
						// quería visitar antes del inicio de sesión
						.defaultSuccessUrl("/products", false)
						.permitAll()
				)
				// Gestión Logout --> cerrar sesión y redirect a /
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
						.permitAll()
				)
				// csrf para protección contra acciones maliciosas --> uso de tokens
				.csrf(csrf -> csrf
						.ignoringRequestMatchers("/h2-console/**")
				)
				.headers(headers -> headers
						.frameOptions(frame -> frame.sameOrigin())
				);
				
				
				
		return http.build();
	}
	
	@Bean
	public UserDetailsService users() {
		UserDetails admin = User.withUsername("admin")
				.password("{noop}admin")
				.roles("ADMIN")
				.build();
		UserDetails user = User.withUsername("user")
				.password("{noop}user")
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(admin, user);
	}
	
}
