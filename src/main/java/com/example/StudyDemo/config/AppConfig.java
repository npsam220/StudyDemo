package com.example.StudyDemo.config;

import com.example.StudyDemo.service.BeanService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    @Bean
    public BeanService beanService() {
        return new BeanService();
    }
}