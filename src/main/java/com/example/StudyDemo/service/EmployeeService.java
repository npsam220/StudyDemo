package com.example.StudyDemo.service;

import com.example.StudyDemo.entity.Employee;
import com.example.StudyDemo.exception.EmployeeNotFoundException;
import com.example.StudyDemo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }
    public Employee findById(Long id) {
        /* 
        System.out.println("EmployeeService findById:"+id);
        return repository.findById(id).orElse(null);
        */
         log.info("社員検索開始 id={}", id);

    return repository.findById(id)
        .orElseThrow(() -> {
            log.error("社員が見つかりません id={}", id);
            return new EmployeeNotFoundException(":社員が見つかりません");
        });
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