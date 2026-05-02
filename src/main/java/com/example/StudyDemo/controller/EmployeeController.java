package com.example.StudyDemo.controller;

import com.example.StudyDemo.entity.Employee;
import com.example.StudyDemo.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Tag(name = "社員管理")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }
    // 🔥 查詢（Native SQL）
    @GetMapping("/search")
    @Operation(summary = "社員を検索")  
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
    @Operation(summary = "全社員を取得")
    public List<Employee> getAll() {
        return service.getAll();
    }

    // 新增
    @PostMapping
    @Operation(summary = "社員を作成")
    public Employee create(@RequestBody Employee emp) {
        System.out.println("create");
        return service.save(emp);
    }
    @GetMapping("/{id}")
    @Operation(summary = "指定されたIDの社員を取得")
    public Employee findById(@PathVariable Long id) {
        return service.findById(id);
    }

    // 更新
    @PutMapping("/{id}")
    @Operation(summary = "社員を更新")
    public Employee update(@PathVariable Long id, @RequestBody Employee emp) {
        emp.setId(id);
        return service.save(emp);
    }

    // 刪除
    @DeleteMapping("/{id}")
    @Operation(summary = "社員を削除")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}