package com.LGDXSCHOOL._dx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF 비활성화
                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll() // 모든 요청 허용
                .anyRequest().permitAll() // 인증 없이 허용
                .and()
                .formLogin().disable(); // 기본 로그인 페이지 비활성화

        return http.build();
    }
}
