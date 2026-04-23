package com.example.StudyDemo.config;

import com.example.StudyDemo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * 🔐 セキュリティの基本設定
     * ・どのリクエストが認証（ログイン）を必要とするかを定義
     * ・フォームログイン機能を有効化
     */
  @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // ❌ CSRF対策を無効化（フロント・バック分離や検証時によく使用）
            .csrf(csrf -> csrf.disable())

            // 🔒 すべてのリクエストは認証が必要
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )

            // 🟢 フォームログインを有効化（/login ページが自動生成される）
            .formLogin(form -> form
                .permitAll() // ログインページは誰でもアクセス可能
                .defaultSuccessUrl("/index.html") // ログイン成功後の遷移先
            )

            // 🚪 ログアウト機能は全ユーザーに許可
            .logout(logout -> logout.permitAll());

        return http.build();
    }

/**
     * ===== 🔴 httpBasic版（現在使用） =====
     * ・API認證用
     */ 
/* 
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable())

        .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated()
        )

        // 🔥 改這裡
        .httpBasic();   // ← 重點

    return http.build();
}
*/
    /**
     * 🔑 認証マネージャー（AuthenticationManager）の設定
     *
     * 👉 ここが重要ポイント：
     * Spring Security に対して以下を設定する
     *
     * 1️⃣ CustomUserDetailsService を利用してユーザー情報をDBから取得
     * 2️⃣ PasswordEncoder（BCryptなど）でパスワードを照合
     */
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            CustomUserDetailsService userDetailsService
    ) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)

                // 👇 カスタムUserDetailsServiceを指定
                .userDetailsService(userDetailsService)

                // 👇 パスワードエンコーダーを指定（例：BCrypt）
                .passwordEncoder(passwordEncoder)

                // 👇 AuthenticationManagerを生成
                .and()
                .build();
    }
}