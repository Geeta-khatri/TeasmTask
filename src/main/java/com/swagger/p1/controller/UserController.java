package com.swagger.p1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.swagger.p1.DTO.UsersDTO;
import com.swagger.p1.DTO.authRequestDTO;
import com.swagger.p1.Service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController("/UsersInfo")
public class UserController {

    @Autowired
    private UsersService userService;

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody UsersDTO userdto) {
        return userService.registreUser(userdto);
        
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody authRequestDTO authReq) {
       
        return userService.loginUser(authReq);
    }
    
    
}
