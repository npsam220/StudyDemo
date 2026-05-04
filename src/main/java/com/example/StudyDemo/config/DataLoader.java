package com.example.StudyDemo.config;

import com.example.StudyDemo.entity.AuthUser;
import com.example.StudyDemo.entity.Employee;
import com.example.StudyDemo.repository.AuthUserRepository;
import com.example.StudyDemo.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    /**
     * 初始化預設帳號（admin / 1234）
     */
    @Bean
    CommandLineRunner initUser(AuthUserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                AuthUser user = new AuthUser();
                user.setUsername("admin");
                user.setPassword(encoder.encode("1234"));
                user.setRole("ADMIN");
                repo.save(user);

                System.out.println("👉 預設帳號已建立：admin / 1234");
            }
        };
    }

    /**
     * 初始化員工測試資料
     */
    @Bean
    CommandLineRunner initEmployee(EmployeeRepository repo) {
        return args -> {

            if (repo.count() == 0) { // 避免重複塞資料

                Employee e1 = new Employee();
                e1.setName("Sam");
                e1.setAge(25);
                e1.setDepartment("IT");
                e1.setEmail("sam@test.com");

                Employee e2 = new Employee();
                e2.setName("Mike");
                e2.setAge(30);
                e2.setDepartment("HR");
                e2.setEmail("mike@test.com");

                Employee e3 = new Employee();
                e3.setName("John");
                e3.setAge(28);
                e3.setDepartment("Sales");
                e3.setEmail("john@test.com");

                repo.save(e1);
                repo.save(e2);
                repo.save(e3);

                System.out.println("👉 測試員工資料已建立");
            }
        };
    }
}