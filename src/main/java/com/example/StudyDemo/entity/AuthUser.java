package com.example.StudyDemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class AuthUser {

    // 🔹 主キー（自動採番）
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 ユーザー名（ログイン用・一意）
    @Column(nullable = false, unique = true)
    private String username;

    // 🔹 パスワード（暗号化して保存）
    @Column(nullable = false)
    private String password;

    // 🔹 ロール（例：ADMIN / USER）
    @Column(nullable = false)
    private String role;

    // ===== Getter / Setter =====  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }   

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }   

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}