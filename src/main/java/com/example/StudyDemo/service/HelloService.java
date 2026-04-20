package com.example.StudyDemo.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String getMessage() {
        return "Hello Spring Boot";
    }
}