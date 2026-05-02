package com.example.StudyDemo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(
    name = "ユーザー管理",
    description = "ユーザーのCRUD操作および検索機能"
)
public class AuthController {

    @GetMapping("/me")
    public Map<String, Object> getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> result = new HashMap<>();
        result.put("username", auth.getName());
        result.put("roles", auth.getAuthorities());

        return result;
    }
}
