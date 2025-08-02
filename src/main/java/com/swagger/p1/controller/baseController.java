package com.swagger.p1.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class baseController {
    
    @GetMapping("/")
    public String getHelloMethod() {
        return "hello";
    }
    @PostMapping("/path")
    public String postNameMethod(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
}
