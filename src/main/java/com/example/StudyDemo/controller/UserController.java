package com.example.StudyDemo.controller;



import com.example.StudyDemo.entity.User;
import com.example.StudyDemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("id/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }
    @GetMapping("name/{name}")
    public List<User> getByＮame(@PathVariable String name) {
        return userService.getByName(name);
    }
    @GetMapping("/searchByName")
    public List<User> searchByName(@RequestParam String name) {
        return userService.searchByName(name);
    }
    @GetMapping("/searchByNameByJPQL")
    public List<User> searchByNameByJPQL(@RequestParam String name) {
        return userService.searchByNameByJPQL(name);
    }
    @GetMapping("/search")
    public List<User> search(
            @RequestParam String name,
            @RequestParam Integer age
    ) {
        return userService.getByNameAndAge(name, age);
    }
    @PostMapping
    public User create(@RequestBody @Valid User user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}