package com.swagger.p1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swagger.p1.Entity.Users;
import com.swagger.p1.Service.UsersService;
import com.swagger.p1.repository.UsersRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AuthController {
	 
	    private final UsersService userService;
	    
	    private  final UsersRepo urepo;
	 
	@PostMapping("/roleAssign/{id}")
	public String AssignUserRole(@PathVariable Long id,@RequestParam String role) {
		System.out.println("id is "+id +" "+ "role is "+ role);
		Users userExists=urepo.findById(id).orElse(null);
		
		if(userExists==null) {
			return "User does not exists for roe assigning";
		}
		else {
			userExists.setAssigned_role(role);
			System.out.println("users info1 "+userExists.getAssigned_role());
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
