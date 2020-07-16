package com.talissonmelo.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FinanceApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(FinanceApplication.class, args);
	}

}
