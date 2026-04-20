package com.example.StudyDemo.repository;

import com.example.StudyDemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // 🔥 Native SQL（今天重點）
    @Query(value = "SELECT * FROM employee WHERE name LIKE %:name%", nativeQuery = true)
    List<Employee> searchByNameNative(@Param("name") String name);
    // 🔥 Native SQL（今天重點）
    @Query(value = """
    SELECT * FROM employee
    WHERE (:id IS NULL OR id = :id)
      AND (:name IS NULL OR name LIKE %:name%)
      AND (:age IS NULL OR age = :age)
      AND (:department IS NULL OR department = :department)
      AND (:email IS NULL OR email LIKE %:email%)
    """, nativeQuery = true)
    List<Employee> search(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("age") Integer age,
            @Param("department") String department,
            @Param("email") String email
    );
}
