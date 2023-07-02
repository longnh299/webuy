package com.webuy.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new WebuyUserDetailService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http
	.authenticationProvider(authenticationProvider())
	.authorizeHttpRequests()
	.requestMatchers("/users/**").hasAuthority("Admin")
	.requestMatchers("/categories/**").hasAnyAuthority("Admin", "Editor")
	.requestMatchers("/brands/**").hasAnyAuthority("Admin", "Editor") 
	.requestMatchers("/products/**").hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper") 
	.requestMatchers("/customers/**").hasAnyAuthority("Admin", "Salesperson")
	.requestMatchers("/shipping/**").hasAnyAuthority("Admin", "Salesperson")
	.requestMatchers("/report/**").hasAnyAuthority("Admin", "Salesperson")
	.requestMatchers("/orders/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
	.requestMatchers("/articles/**").hasAnyAuthority("Admin", "Editor") 
	.requestMatchers("/menus/**").hasAnyAuthority("Admin", "Editor") 
	.anyRequest()
	.authenticated()
	.and()
	.formLogin()
		.loginPage("/login")
		.usernameParameter("email")
		.permitAll()
	.and().logout().permitAll()
	.and().rememberMe().key("toilanguyenhoanglongtoilanguyenhoanglong").tokenValiditySeconds(7 * 24 * 60 * 60); // use fixed key to when restart app , old cookie can be used, default cookies time is 14 days
	return http.build();
	}
	

	
}
