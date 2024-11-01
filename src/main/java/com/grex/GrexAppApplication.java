package com.grex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class GrexAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrexAppApplication.class, args);
	}

}
