package com.example.KafkaToFile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.yml")
@SpringBootApplication
public class KafkaToFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaToFileApplication.class, args);
	}

}
