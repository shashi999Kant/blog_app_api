package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.filter.JwtAuthFilter;
import com.blog.service.UserInfoService;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {

	public static final String[] PUBLIC_URLS = { "/api/auth/welcome", "/api/auth/addNewUser", "/api/auth/generateToken",
			"/v3/api-docs/**", "/v2/api-docs/**","/swagger-ui/**", "/swagger-resources/**", "/webjars/**","/" };

	@Autowired
	private JwtAuthFilter authFilter;

	// User Creation
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserInfoService();
	}

	// Configuring HttpSecurity
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeHttpRequests()
				.requestMatchers(PUBLIC_URLS).permitAll()
				.requestMatchers(HttpMethod.GET).permitAll()
				.and().authorizeHttpRequests().requestMatchers("/api/**").authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	// Password Encoding
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	
	
	@Bean
	public FilterRegistrationBean coresFilter() {
		
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfiguration = new CorsConfiguration();
		
		corsConfiguration.setAllowCredentials(true);
         corsConfiguration.addAllowedOriginPattern("*");
         corsConfiguration.addAllowedHeader("Authorization");
         corsConfiguration.addAllowedHeader("Content-Type");
         corsConfiguration.addAllowedHeader("Accept");
         corsConfiguration.addAllowedMethod("POST");
         corsConfiguration.addAllowedMethod("GET");
         corsConfiguration.addAllowedMethod("DELETE");
         corsConfiguration.addAllowedMethod("PUT");
         corsConfiguration.addAllowedMethod("OPTIONS");
         corsConfiguration.setMaxAge(3600L);
         
         
		
		source.registerCorsConfiguration("/**", corsConfiguration);
		
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(-110);
		
		return bean;
	}
}
