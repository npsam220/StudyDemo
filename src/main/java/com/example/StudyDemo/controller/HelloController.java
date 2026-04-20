package com.example.StudyDemo.controller;


import com.example.StudyDemo.service.ComponetService;
import com.example.StudyDemo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;
    @Autowired
    ComponetService componetService;
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }
    @GetMapping("/getComponentService")
    public String getComponentService() {
        return componetService.getComponetService();
    }
    @GetMapping("/helloSerive")
    public String helloSerive() {
        return helloService.getMessage();
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
