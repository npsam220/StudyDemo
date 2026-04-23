package com.example.StudyDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class StudyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyDemoApplication.class, args);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoded = encoder.encode("1234");
        System.out.println("encoded:"+encoded);
	}

}
