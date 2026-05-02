package com.example.StudyDemo.springBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.StudyDemo.entity.Employee;
import com.example.StudyDemo.repository.EmployeeRepository;
import com.example.StudyDemo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeService service;

    @Autowired
    private EmployeeRepository repository;

    @Test
    void testFindById_realDB() {

        Employee emp = new Employee();
        emp.setName("Sam");

        repository.save(emp);

        Employee result = service.findById(emp.getId());

        assertEquals("Sam", result.getName(), "社員名は「Sam」であるべきです。");
    }
}
