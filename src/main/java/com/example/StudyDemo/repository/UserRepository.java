package com.example.StudyDemo.repository;

import com.example.StudyDemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    List<User> findByNameAndAge(String name, Integer age);
    List<User> findByNameContaining(String name);
    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    List<User> searchByNameByJPQL(@Param("name") String name);
}
