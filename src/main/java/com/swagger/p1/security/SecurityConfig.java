package com.swagger.p1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    JwtTokenFilter jwtTokenFilter;
//  @Bean
//     public JwtTokenFilter jwtTokenFilter(JWTConfig jwtConfig) {
//         JwtTokenFilter filter = new JwtTokenFilter();
//         filter.setJwtConfig(jwtConfig);  // You’ll need to add a setter in JwtTokenFilter
//         return filter;
    //}
    //  @Bean
    // public JwtTokenFilter jwtTokenFilter(JWTConfig jwtConfig) {
    //     return new JwtTokenFilter(jwtConfig);
    // }
    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http,JwtTokenFilter jwtTokenFilter) throws Exception{

        http.csrf(csrf->csrf.disable())
        .authorizeHttpRequests(auth->auth
        .requestMatchers("/UsersInfo/login")
        .permitAll()
        .anyRequest()
        .authenticated()
        )
        .addFilterBefore(jwtTokenFilter,  UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
