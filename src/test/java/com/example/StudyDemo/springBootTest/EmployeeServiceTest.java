package com.example.StudyDemo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.StudyDemo.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
public class EmployeeServiceTest {

    @Autowired
     private EmployeeService service;
     @Test
     void test() {
        System.out.println("テスト開始");
        assertTrue(true); // 必ず成功するテスト（テスト機構の確認のため）
     }
     @Test
     void testFindById() {
        Employee emp = service.findById(1L);

        assertNotNull(emp);
     }
}
