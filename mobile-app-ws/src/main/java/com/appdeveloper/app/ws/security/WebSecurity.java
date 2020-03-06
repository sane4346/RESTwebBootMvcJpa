package com.appdeveloper.app.ws.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appdeveloper.app.ws.service.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{

	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity(	UserService userDetailsService,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
	//	logger.debug("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");

		http.csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.POST, SecurityConstants.SING_UP_URL).permitAll()
			.anyRequest().authenticated().and().addFilter(getAuthenticationFilter())
			.addFilter(new AuthorizationFilter(authenticationManager()))
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override 
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	public  AuthenticationFilter getAuthenticationFilter() throws Exception {
		final AuthenticationFilter authFilter = new AuthenticationFilter(authenticationManager());
		authFilter.setFilterProcessesUrl("/users/login");
		return authFilter;
		
	}
}
