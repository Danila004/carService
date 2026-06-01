package ru.vsu.sheluhin.carService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class CarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}

}
