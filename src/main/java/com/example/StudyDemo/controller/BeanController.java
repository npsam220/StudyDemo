package com.example.StudyDemo.controller;

import com.example.StudyDemo.service.BeanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeanController {

    private final BeanService beanService;

    public BeanController(BeanService beanService) {
        this.beanService = beanService;
    }

    @GetMapping("/bean")
    public String bean() {
        return beanService.getMessage();
    }
}