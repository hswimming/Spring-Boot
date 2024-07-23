package com.shinhan.firstzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA가 감지할 수 있음
@SpringBootApplication
public class FirstzoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstzoneApplication.class, args);
	}

}