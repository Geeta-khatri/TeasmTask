package com.swagger.p1.security;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;

//@Component
@Service
public class JWTConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    public String getSecretKey() {
        return secretKey;
    }
    public long getExpiration() {
        return expiration;
    }
private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    //private final Key k=Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    public String generateToken(String username){
        return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+expiration))
        .signWith(getSigningKey(),SignatureAlgorithm.HS256)
        .compact();
    }

    public String validateToken(String token){
        try {
           return  Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();

        } catch (JwtException e) {
            return null;
        }
    }
}
