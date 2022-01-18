package com.elena.schoolMotivatorAppServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolMotivatorAppServerApplication {
	private static Initializer initializer;
	public SchoolMotivatorAppServerApplication(Initializer initializer) {
		this.initializer = initializer;
	}

	public static void main(String[] args) {
		SpringApplication.run(SchoolMotivatorAppServerApplication.class, args);
		Initializer.createAdmin();
	}

}
