package com.swagger.p1.security;

import org.springframework.stereotype.Service;

import com.swagger.p1.DTO.RefreshTokenReqDTO;
import com.swagger.p1.DTO.TokenDTOResponse;
import com.swagger.p1.DTO.TokenDTOResponse;
import com.swagger.p1.Entity.RefreshToken;
import com.swagger.p1.Entity.Users;
import com.swagger.p1.repository.RefreshTokenRepo;
import com.swagger.p1.repository.UsersRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//@Component
@Service
@RequiredArgsConstructor
public class JWTConfig {

	private final RefreshTokenRepo RefreshTokenRepo;
	
	private final UsersRepo urepo;
	
	
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.RefreshTokenExpiration}")
    private long refreshTokenExpiration;
    
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
    public TokenDTOResponse generateToken(Users  verified_user){
    	TokenDTOResponse tokenResp=new TokenDTOResponse();
    	RefreshToken RefreshToken_obj=new RefreshToken();
    	RefreshToken_obj.setUsers(verified_user);
    	RefreshToken_obj.setRefreshTokenExpiration(refreshTokenExpiration);
    	tokenResp.setJwtToken(Jwts.builder()
        .setSubject(verified_user.getUserName())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+expiration))
        .claim("role", verified_user.getAssigned_role()) 
        .signWith(getSigningKey(),SignatureAlgorithm.HS256)
        .compact());
    	RefreshToken_obj.setTokenMsg(UUID.randomUUID().toString());
    	System.out.println("RefreshToken_obj "+RefreshToken_obj);
    	RefreshTokenRepo.save(RefreshToken_obj);
    	tokenResp.setRefreshTokenUUID(RefreshToken_obj.getTokenMsg());
    	
    	return tokenResp;
    	
    	
    }

    //method to generate Refresh token
    public ResponseEntity<?> generateRefreshToken(RefreshTokenReqDTO tokenMsg){
    	try {
    		Optional<RefreshToken> token_from_db=RefreshTokenRepo.findByTokenMsg(tokenMsg.getTokenMsg());
    		System.out.println("is it empty "+token_from_db.isEmpty());
    		if(token_from_db.isEmpty()) {
    			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized token , refresh token can't be generated");
    		}
    		else {
    			Optional<Users> User_from_tokenMsg=urepo.findById(token_from_db.get().getUsers().getId());
        String token_generated= Jwts.builder()
        .setSubject(User_from_tokenMsg.get().getUserName())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+refreshTokenExpiration))
        .claim("role", User_from_tokenMsg.get().getAssigned_role()) 
        .signWith(getSigningKey(),SignatureAlgorithm.HS256)
        .compact();
        
        return ResponseEntity.status(HttpStatus.OK).body(token_generated);
    	}
    	}
    	catch (JwtException e) {
            return null;
        }
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
