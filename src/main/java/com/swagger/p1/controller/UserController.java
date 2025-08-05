package com.swagger.p1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.swagger.p1.DTO.AuthResponseDTO;
import com.swagger.p1.DTO.UsersDTO;
import com.swagger.p1.DTO.authRequestDTO;
import com.swagger.p1.Service.UsersService;
import com.swagger.p1.Entity.*;
import com.swagger.p1.repository.*;
import com.swagger.p1.security.JWTConfig;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/UsersInfo")
public class UserController {

    @Autowired
    private JWTConfig jwtconfig;

    @Autowired
    private UsersRepo urepo;
    @Autowired
    private UsersService userService;

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody UsersDTO userdto) {
        return userService.registreUser(userdto);
        
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody authRequestDTO authReq) {
        System.out.println("entered the login endpoint");
        Users toVerify=null;
       Optional<Users> getUser=urepo.findByUserName(authReq.getUsername());
       System.out.println("looking for user "+getUser.get().getUserName());
        if(getUser.isPresent() ){

         toVerify=getUser.get();
        
         System.out.println(toVerify.getUserName());
        if(toVerify.getUserName().equals(authReq.getUsername()) && toVerify.getPassword().equals(authReq.getPassword())){
        System.out.println("user found"+toVerify);
            String token =jwtconfig.generateToken(authReq.getUsername());
            return ResponseEntity.ok(new AuthResponseDTO(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credintial");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credintial");
        }
    }
}
