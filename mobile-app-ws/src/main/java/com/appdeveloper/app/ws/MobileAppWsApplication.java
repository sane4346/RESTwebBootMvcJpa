package com.appdeveloper.app.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//import com.appdeveloper.app.ws.ui.controller.UserController;

@SpringBootApplication
//@ComponentScan(basePackageClasses = UserController.class)
public class MobileAppWsApplication {

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
	
}
