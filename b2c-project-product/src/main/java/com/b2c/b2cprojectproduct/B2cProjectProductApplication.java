package com.b2c.b2cprojectproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class B2cProjectProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(B2cProjectProductApplication.class, args);
	}

	/**
	 * rest远程调用，注册RestTemplate
	 * @return
	 */
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
