package com.swagger.p1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.swagger.p1.DTO.*;
import com.swagger.p1.Service.TaskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/Task")
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

    @PutMapping("/Update/{id}")
    public ResponseEntity<?> putMethodName(@PathVariable Long id, @RequestBody TaskDTO t) {
         return tservice.updateTask(id,t);
    }
    
}
