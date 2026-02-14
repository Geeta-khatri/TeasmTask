package com.swagger.p1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.swagger.p1.DTO.*;
import com.swagger.p1.Entity.Users;
import com.swagger.p1.Service.ProjectService;
import com.swagger.p1.repository.UsersRepo;
import com.swagger.p1.security.JWTConfig;

import io.jsonwebtoken.Claims;

import com.swagger.p1.security.JWTConfig;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final JWTConfig jwtconfig;
	private final UsersRepo urepo;
    private final ProjectService pservice;

    @PostMapping("/admin/createProject")
    public ResponseEntity<?> projectcreate(@RequestBody ProjectDTO pdto) {
        return pservice.createProject(pdto);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> projectDelete(@PathVariable Long id){
        return pservice.deleteProject(id);
    }
    
    @PutMapping("/admin/update/{id}")
     public ResponseEntity<?> projectUpdate(@PathVariable Long id,@RequestBody ProjectDTO pdto){
        return pservice.updateProject(id,pdto);
    }

    @GetMapping("/admin/getAllProject")
    public ResponseEntity<?> getAllProject() {
        return pservice.AllProject();
    }
    
    //getting project assigned to logged in user
    @GetMapping("/getProject")
    public ResponseEntity<?> projectByUserId(HttpServletRequest req) {
    	Claims claim=jwtconfig.getClaimsDtls(req);
    	if(claim!=null) {
    		Users u= urepo.findByUserName(claim.getSubject()).orElse(null);
    		if(u!=null) {
        return pservice.projectByUserId(u.getId());
    		}
    		else {
    			return new ResponseEntity<>("User does not exists",HttpStatus.BAD_REQUEST);
    		}
    	}
    	return new ResponseEntity<>("Invalid Login",HttpStatus.UNAUTHORIZED);
    }
    
    
}
