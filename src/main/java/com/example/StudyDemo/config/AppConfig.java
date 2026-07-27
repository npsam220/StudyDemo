package com.example.StudyDemo.config;

import com.example.StudyDemo.service.BeanService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    // BeanServiceを@Beanとして登録する
    @Bean
    public BeanService beanService() {
        return new BeanService();
    }

    // PasswordEncoderを@Beanとして登録する
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}