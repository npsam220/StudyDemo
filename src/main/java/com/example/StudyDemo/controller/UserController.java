package com.example.StudyDemo.controller;



import com.example.StudyDemo.entity.User;
import com.example.StudyDemo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "ユーザー管理")  
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Operation(summary = "ユーザー一覧を取得")
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @Operation(summary = "指定されたIDのユーザーを取得")
    @GetMapping("id/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }
    @Operation(summary = "指定された名前のユーザーを取得")
    @GetMapping("name/{name}")
    public List<User> getByＮame(@PathVariable String name) {
        return userService.getByName(name);
    }
    @Operation(summary = "名前でユーザーを検索")
    @GetMapping("/searchByName")
    public List<User> searchByName(@RequestParam String name) {
        return userService.searchByName(name);
    }
    @Operation(summary = "名前でユーザーをJPQLを使って検索")
    @GetMapping("/searchByNameByJPQL")
    public List<User> searchByNameByJPQL(@RequestParam String name) {
        return userService.searchByNameByJPQL(name);
    }
    @Operation(summary = "名前と年齢でユーザーを検索")
    @GetMapping("/search")
    public List<User> search(
            @RequestParam String name,
            @RequestParam Integer age
    ) {
        return userService.getByNameAndAge(name, age);
    }
    @Operation(summary = "ユーザーを作成")
    @PostMapping
    public User create(@RequestBody @Valid User user) {
        return userService.save(user);
    }
    @Operation(summary = "ユーザーを更新")
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userService.save(user);
    }
    @Operation(summary = "ユーザーを削除")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}