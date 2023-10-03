package com.example.sysid.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security の認証機能を一部を除き無効化
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // statelessの場合はCSRFチェックをOFF
                .authorizeHttpRequests(customizer -> customizer
                .requestMatchers("/manage").authenticated() // /manageは認証が必要なページ
                .anyRequest().permitAll()); // その他のリクエストはすべて許可
        return http.build();
    }
}
