package com.swagger.p1.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.swagger.p1.DTO.*;
import com.swagger.p1.Entity.Users;
import com.swagger.p1.Service.TaskService;
import com.swagger.p1.repository.UsersRepo;
import com.swagger.p1.security.JWTConfig;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequiredArgsConstructor
@RequestMapping("/Task")
public class TaskController {

	private final JWTConfig jwtConfig;
    
	private final UsersRepo urepo;
    private final TaskService tservice;

    @PostMapping("/admin/taskCreate")
    public ResponseEntity<?> TaskCreate(@RequestBody TaskDTO tdto) {
        return tservice.createTask(tdto);
       
    }
// this method is secure here i will get the authenticated user and will return task assigned to that user only
   
    @GetMapping("/user")
    public ResponseEntity<?> TaskAllGetUserDummy(HttpServletRequest req) {
    	String authHeader=req.getHeader("Authorization");
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            String jwt=authHeader.substring(7);
            String username=jwtConfig.validateToken(jwt);
            if(username==null) {
            	System.out.println("insinde null username");
            	return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exists");
            }
          Users u= urepo.findByUserName(username).orElse(null);
          if(u!=null){
        		System.out.println("found user "+u.getId());
           return tservice.UsergetAllTask(u.getId());
          }
         
          
        }
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        //return tservice.UsergetAllTask(USerid);
    }
    @PutMapping("/admin/Update/{id}")
    public ResponseEntity<?> TaskUpdate(@PathVariable Long id, @RequestBody TaskDTO t) {
         return tservice.updateTask(id,t);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> TaskDelete(@PathVariable Long id) {
         return tservice.deleteTask(id);
    }

    @GetMapping("/admin/AllTask")
    public ResponseEntity<?> getAllTask() {
        return tservice.getAllTask();
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<?> patchStatus(@PathVariable Long id, @RequestBody TaskDTO tdto){
        return tservice.StatusPatch(id,tdto);
    }


}
