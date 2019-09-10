package com.example.mongo.example1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(
		exclude = {
				ThymeleafAutoConfiguration.class
		}
		
)
@ImportResource(value = {"classpath*:applicationContext.xml"})
public class MohanSpringBootMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MohanSpringBootMongoApplication.class, args);
	}

}
