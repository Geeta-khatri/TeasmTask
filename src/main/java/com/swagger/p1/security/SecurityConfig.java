package com.swagger.p1.security;

import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http) throws Exception{

        http.csrf(csrf->csrf.disable())
        .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth->auth
        .requestMatchers(HttpMethod.POST,"/UsersInfo/login").permitAll()
         .requestMatchers(HttpMethod.POST, "/UsersInfo/register").permitAll()
         .requestMatchers(HttpMethod.GET, "/").permitAll()
         .requestMatchers(HttpMethod.POST,"/admin/roleAssign").hasAuthority("ADMIN")
        .anyRequest()
       .authenticated()
        )
        .formLogin(AbstractHttpConfigurer::disable) // Disable form login
            .httpBasic(AbstractHttpConfigurer::disable)
        .addFilterBefore(jwtTokenFilter,  UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
