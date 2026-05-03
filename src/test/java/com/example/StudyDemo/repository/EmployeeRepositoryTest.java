package com.example.StudyDemo.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.StudyDemo.entity.Employee;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Test
    void testFindById() {
        Employee emp = new Employee();
        emp.setName("Sam");

        repository.save(emp);

        Optional<Employee> result = repository.findById(emp.getId());

        assertTrue(result.isPresent());
        assertEquals("Sam", result.get().getName());
    }
}
