package com.example.searchPracticeBase.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers("/", "home", "/images/**", "/icons/favicon.ico", "/admin/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginPage("/admin")
                        .permitAll()
                )
                .oauth2Login(auth ->{
                    auth.defaultSuccessUrl("/main");
                    auth.loginPage("/oauth2/authorization/google");
                })
                .logout(logout -> logout.invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID").permitAll())
                .build();
    }
}
