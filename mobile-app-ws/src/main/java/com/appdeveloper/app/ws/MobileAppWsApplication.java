package com.appdeveloper.app.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appdeveloper.app.ws.security.AppProperties;

//import com.appdeveloper.app.ws.ui.controller.UserController;

@SpringBootApplication
//@ComponentScan(basePackageClasses = UserController.class)
public class MobileAppWsApplication extends SpringBootServletInitializer{

	//overriden method from Spring boot Servlet Initializer
	@Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
		return applicationBuilder.sources(MobileAppWsApplication.class);
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(MobileAppWsApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder bCrypPassowrdEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SpringApplicationContext springApplicationContext()
	{
		return new SpringApplicationContext();
	}
	
	@Bean(name = "AppProperties")
	AppProperties getAppProperties()
	{
		return new AppProperties();
	}
	
}
