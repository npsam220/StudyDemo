package com.example.StudyDemo.service;

import com.example.StudyDemo.entity.AuthUser;
import com.example.StudyDemo.repository.AuthUserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository repo;

    public CustomUserDetailsService(AuthUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        System.out.println("登入帳號: " + username);
        AuthUser user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                 System.out.println("role from DB: " + user.getRole());
        System.out.println("authorities: " + 
         org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPassword())
        .roles(user.getRole())
        .build()
        .getAuthorities());
        System.out.println("user.getPassword:"+user.getPassword());
         System.out.println("DB使用者: " + user.getUsername());
        return org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPassword())
        .roles(user.getRole())  // ⭐ 用 DB 的角色
        .build();
    }

}