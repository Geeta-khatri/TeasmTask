package com.swagger.p1.Service;

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.swagger.p1.DTO.UsersDTO;
import com.swagger.p1.Entity.Users;
import com.swagger.p1.config.RedisConfig;
import com.swagger.p1.repository.*;
import com.swagger.p1.security.JWTConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
@Service
public class UsersService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  UsersRepo urepo;
    
    @Autowired
    private JWTConfig jwtConfig;
    
    @Autowired
    private RedisConfig redisConfig;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public ResponseEntity<String>  registreUser(UsersDTO uu){
        if(uu.getUsername()==null || uu.getUsername().trim().isEmpty()){
            return new ResponseEntity<>("User name can not be empty",HttpStatus.BAD_REQUEST);
        }
        Users existingUsers=urepo.findByUserName(uu.getUsername()).orElse(null);
        if(existingUsers!=null){
        return new ResponseEntity<>("User name  already exists",HttpStatus.CONFLICT);
        }
        else {
            Users registU=new Users();
            registU.setUserName(uu.getUsername());
            registU.setEmail(uu.getEmail());
            registU.setPassword(passwordEncoder.encode(uu.getPassword()));
            try {
                urepo.save(registU);
                return new ResponseEntity<>("User Saved Successfully",HttpStatus.OK);
                
            } catch (Exception e) {
                return new ResponseEntity<>("An error occurred while saving the user",HttpStatus.INTERNAL_SERVER_ERROR);
                
            }
    }
    }
    // public ResponseEntity<?> loginUser(authRequestDTO authreq){

	public ResponseEntity<String> logout(String token) {
		
		String uname=jwtConfig.validateToken(token);
		if(!uname.isEmpty()) {
			Date expirationDate  =jwtConfig.etxractClaims(token).getBody().getExpiration();
			
			long remainingMillis = expirationDate.getTime()-System.currentTimeMillis();
			System.out.println("remainingMillis is "+remainingMillis);
			if(remainingMillis>0) {
				blacklist(token,remainingMillis);
			}
			
		}
		
		return   ResponseEntity.ok("Logged out successfully");
	}
	
	
	private void blacklist(String token,long  d) {
		System.out.println("inside blacklist "+d);
		redisTemplate.opsForValue()
        .set(token, "BLACKLISTED", Duration.ofMillis(d));
		System.out.println("redisTemplate is "+redisTemplate);
	}
        

   

   
}
