package com.example.StudyDemo.config;

import com.example.StudyDemo.service.BeanService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {


    @Bean
    public BeanService beanService() {
        return new BeanService();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
    }
}