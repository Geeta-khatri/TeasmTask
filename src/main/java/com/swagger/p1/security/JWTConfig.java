package com.swagger.p1.security;

import org.springframework.stereotype.Service;

import com.swagger.p1.Entity.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

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
    public String generateToken(Users  verified_user){
        return Jwts.builder()
        .setSubject(verified_user.getUserName())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+expiration))
        .claim("role", verified_user.getAssigned_role()) 
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
    
    public String extractRole(String token) {
    	try {
    		return Jwts.parserBuilder()
    	            .setSigningKey(getSigningKey())
    	            .build()
    	            .parseClaimsJws(token)
    	            .getBody()
    	            .get("role",String.class);
    	            

    		
    	}
    	 catch (JwtException e) {
             return null;
         }
    }
    
    public Claims getClaimsDtls(HttpServletRequest req) {
    	
    		String authHeader =req.getHeader("Authorization");
        	if(authHeader!=null && authHeader.startsWith("Bearer ")){
        	String token =authHeader.substring(7);
        	String uname=validateToken(token);
        	if(uname!=null) {
        		try {
    		return Jwts.parserBuilder()
    	            .setSigningKey(getSigningKey())
    	            .build()
    	            .parseClaimsJws(token)
    	            .getBody();
    	            
        	}
        		catch (JwtException e) {
                    return null;
                }
    	}
        	else {
        		System.out.println("Token validation failed: invalid token");
                return null;
        	}
        	}
        	System.out.println("Authorization header missing or does not start with Bearer");
            return null;
    }
}
