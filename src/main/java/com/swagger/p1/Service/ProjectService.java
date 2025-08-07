package com.swagger.p1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.swagger.p1.Entity.*;
import com.swagger.p1.DTO.ProjectDTO;
import com.swagger.p1.DTO.ProjectDTOResponse;
import com.swagger.p1.repository.ProjectRepo;
import com.swagger.p1.repository.UsersRepo;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
@Service
public class ProjectService {

    @Autowired
    private UsersRepo urepo;

    @Autowired
    private ProjectRepo prepo;

    public ResponseEntity<?> createProject(ProjectDTO pDTO){
        ProjectDTOResponse response=new ProjectDTOResponse();
        Optional<Project> existingProject=prepo.findByName(pDTO.getName());
        if(existingProject.isPresent()){
       // return new ResponseEntity<>("Project Already exists",HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Project already exists");
        }
        Users AssigningTo=null;
        if(pDTO.getUserId()!=null){
        Optional<Users> ProjectOwner=urepo.findById(pDTO.getUserId());
        if(ProjectOwner.isEmpty()){
        return new ResponseEntity<>("User does not exists to assign the projects",HttpStatus.BAD_REQUEST);
        }
        AssigningTo=ProjectOwner.get();
    }
        try {
            Project createProject=new Project();
            createProject.setName(pDTO.getName());
            createProject.setDescription(pDTO.getDescription());
            if(AssigningTo!=null){
            createProject.setProjectOwner(AssigningTo);
            }
            Project savedProject=prepo.save(createProject);
            response.setId(savedProject.getId());
            response.setDescription(savedProject.getDescription());
            response.setName(savedProject.getName());
            response.setUserId(pDTO.getUserId());
            Map<String,Object> createProjectResponse=new HashMap<>();
            createProjectResponse.put("message","Project created Successfully");
            createProjectResponse.put("project", response);
            return new ResponseEntity<>(createProjectResponse,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An Exception occurred in creating project ",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> deleteProject(Long id){
        try {
            Optional<Project> pExist=prepo.findById(id);
            if(pExist.isPresent()){
                prepo.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("Project deleted successully!");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO project exist with mention project id");
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occured in deleting project");
        }
    }
}

