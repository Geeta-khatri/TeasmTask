package com.swagger.p1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.swagger.p1.DTO.*;
import com.swagger.p1.Service.ProjectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService pservice;

    @PostMapping("/createProject")
    public ResponseEntity<?> projectcreate(@RequestBody ProjectDTO pdto) {
        return pservice.createProject(pdto);
    }
    
    
    
}
