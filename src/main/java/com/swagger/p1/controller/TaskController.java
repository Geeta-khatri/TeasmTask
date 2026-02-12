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

import io.jsonwebtoken.Claims;
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
    	
        //return tservice.UsergetAllTask(USerid);
     	Claims claim=jwtConfig.getClaimsDtls(req);
    	if(claim!=null) {
    		Users u= urepo.findByUserName(claim.getSubject()).orElse(null);
    		if(u!=null) {
    			return tservice.UsergetAllTask(u.getId());
    		}
    		else {
    			return new ResponseEntity<>("User does not exists",HttpStatus.BAD_REQUEST);
    		}
    	}
    	return new ResponseEntity<>("Invalid Login",HttpStatus.UNAUTHORIZED);
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
