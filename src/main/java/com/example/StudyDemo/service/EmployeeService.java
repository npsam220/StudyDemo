package com.example.StudyDemo.service;

import com.example.StudyDemo.entity.Employee;
import com.example.StudyDemo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }
    public Employee findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // 🔥 查詢（Native SQL）
    public List<Employee> search(Long id,String name,Integer age,String department,String email) {
        if (
                id==null&&name == null&&age==null&&department==null&&email==null
        ) {
            return repository.findAll(); // 空值就查全部（實務很常這樣寫）
        }
        return repository.search(id,name,age,department,email);
    }
    // 🔥 查詢（Native SQL）
    public List<Employee> search(Long id,String name) {
        if (name == null || name.trim().isEmpty()) {
            return repository.findAll(); // 空值就查全部（實務很常這樣寫）
        }
        return repository.searchByNameNative(name);
    }
    // 🔥 查詢（Native SQL）
    public List<Employee> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return repository.findAll(); // 空值就查全部（實務很常這樣寫）
        }
        return repository.searchByNameNative(name);
    }

    // 查全部
    public List<Employee> getAll() {
        return repository.findAll();
    }

    // 新增 / 更新
    public Employee save(Employee emp) {
        return repository.save(emp);
    }

    // 刪除
    public void delete(Long id) {
        repository.deleteById(id);
    }
}