package com.olivares.denunciaservicie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ApiDenunciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDenunciasApplication.class, args);
	}

}
