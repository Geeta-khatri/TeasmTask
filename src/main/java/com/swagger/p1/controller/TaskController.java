package com.swagger.p1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.swagger.p1.DTO.*;
import com.swagger.p1.Service.TaskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController("/Task")
public class TaskController {

    @Autowired
    private TaskService tservice;

    @PostMapping("/taskCreate")
    public ResponseEntity<?> TaskCreate(@RequestBody TaskDTO tdto) {
        return tservice.createTask(tdto);
       
    }

    @GetMapping("/user")
    public ResponseEntity<?> getMethodName(@RequestParam Long USerid) {
        return tservice.getAllTask(USerid);
    }
    
}
