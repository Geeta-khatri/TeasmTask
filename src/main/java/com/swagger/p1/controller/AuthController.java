package com.swagger.p1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swagger.p1.Entity.Users;
import com.swagger.p1.Service.UsersService;
import com.swagger.p1.repository.UsersRepo;

@RestController("/admin")
public class AuthController {
	 @Autowired
	    private UsersService userService;
	    @Autowired
	    private UsersRepo urepo;
	 
	@PostMapping("/roleAssign")
	public String AssignUserRole(Long id, String role) {
		
		Users userExists=urepo.findById(id).orElse(null);
		if(userExists==null) {
			return "User does not exists for roe assigning";
		}
		else {
			userExists.setAssigned_role(role);
			urepo.save(userExists);
			return "Role assigned Successfullyyyy!!";
		}
		
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/userDtl")
	public List<Users> UserDtls(){
		
				return urepo.findAll();
		
	}
}
