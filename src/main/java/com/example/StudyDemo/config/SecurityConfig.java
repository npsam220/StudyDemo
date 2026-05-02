package com.example.StudyDemo.config;

import com.example.StudyDemo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * 🔐 セキュリティ設定（フォームログイン）
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

             // 🔓 Swagger（最上面）
            .requestMatchers(
               "/swagger-ui.html",
                "/swagger-ui/**",
               "/v3/api-docs/**"
            ).permitAll()

            // 🔓 公開頁面
           .requestMatchers("/", "/index.html").permitAll()
           .requestMatchers("/product/product-query.html").permitAll()

            // 🔓 公開 API
           .requestMatchers(HttpMethod.GET, "/products/search").permitAll()

            // 🔒 管理畫面（ADMIN）
            .requestMatchers("/product/product-management.html").hasRole("ADMIN")

            // 🔒 CRUD API（ADMIN）
            .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

            // 🔒 其他全部需要登入
             .anyRequest().authenticated()

            
            )

            // 🔐 フォームログイン（現在使用）
            .formLogin(form -> form
                .defaultSuccessUrl("/index.html", true)
                .permitAll()
            )

            .logout(logout -> logout.permitAll());

        return http.build();
    }

    /**
     * ===== 🔴 httpBasic版（API用・今は未使用） =====
     */
/*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )

            // 🔐 Basic認証
            .httpBasic();

        return http.build();
    }
*/

    /**
     * 🔑 認証マネージャー
     */
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            CustomUserDetailsService userDetailsService
    ) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
}