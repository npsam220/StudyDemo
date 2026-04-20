package com.example.StudyDemo.controller;

import com.example.StudyDemo.entity.Employee;
import com.example.StudyDemo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin // 👉 React 會用到（很重要）
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }
    // 🔥 查詢（Native SQL）
    @GetMapping("/search")
    public List<Employee> search(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String email
    ) {
        System.out.println("searchxxxxxxx");
        List<Employee> employees = service.search(id, name, age, department, email);
        return employees;
    }
    // 🔥 查詢（Native SQL）
//    @GetMapping("/search")
//    public List<Employee> search(
//            @RequestParam(required = false) String name
//    ) {
//        return service.searchByName(name);
//    }

    // 查全部
    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }

    // 新增
    @PostMapping
    public Employee create(@RequestBody Employee emp) {
        System.out.println("create");
        return service.save(emp);
    }
    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return service.findById(id);
    }

    // 更新
    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee emp) {
        emp.setId(id);
        return service.save(emp);
    }

    // 刪除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}