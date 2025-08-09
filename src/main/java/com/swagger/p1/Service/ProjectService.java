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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.List;
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
        Users AssigningTo=new Users();
        System.out.println("userId is"+pDTO.getUserId());
        if(pDTO.getUserId()!=null){
        Optional<Users> ProjectOwner=urepo.findById(pDTO.getUserId());
        System.out.println("Project owner before if is"+ProjectOwner);
        if(ProjectOwner.isEmpty()){
        //return new ResponseEntity<>("User does not exists to assign the projects",HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exists to assign the projects");
        }
        else{
        AssigningTo=ProjectOwner.get();
        System.out.println("Assigned to is "+AssigningTo);
        }
    }
        try {
            Project createProject=new Project();
            createProject.setName(pDTO.getName());
            createProject.setDescription(pDTO.getDescription());
            if(AssigningTo!=null){
            createProject.setProjectOwner(AssigningTo);
            System.out.println("user for creating project"+createProject.getProjectOwner());
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO project exist with mentioned project id");
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occured in deleting project");
        }
    }

    public ResponseEntity<?> updateProject(Long id, ProjectDTO pinp){

        try {
            Optional<Project>  pExist=prepo.findById(id);
            ProjectDTOResponse pResponse=new ProjectDTOResponse();
            if(pExist.isPresent()){
                Project to_update=new Project();
                to_update=pExist.get();
                to_update.setDescription(pinp.getDescription());
                to_update.setName(pinp.getName());
                Optional<Users> assign_to=urepo.findById(pinp.getUserId());
                if(assign_to.isPresent()){
                    System.out.println("owner user is "+assign_to.get().getId());
                to_update.setProjectOwner(assign_to.get());
                System.out.println(to_update.getProjectOwner());
                }
                prepo.save(to_update);
                pResponse.setDescription(to_update.getDescription());
                pResponse.setName(to_update.getName());
                pResponse.setUserId(assign_to.get().getId());
                pResponse.setId(id);
                return ResponseEntity.status(HttpStatus.OK).body("Project updated successfullyy!");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No project exists with mentioned project id");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occure in updating project");
        }
    }

    public ResponseEntity<?> AllProject(){
        try {
            List<Project> Allproject=prepo.findAll();
            System.out.println("project present "+Allproject);
           List< ProjectDTOResponse> presp=new ArrayList<ProjectDTOResponse>();

            if(Allproject.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("no project Exist!!");
            }
            else{
                for(Project i:Allproject){
                    ProjectDTOResponse p= new ProjectDTOResponse();
                    p.setDescription(i.getDescription());
                    p.setId(i.getId());
                    p.setName(i.getName());
                    if(i.getProjectOwner()!=null){
                    p.setUserId(i.getProjectOwner().getId());
                    }
                   presp.add(p);
                }
                return ResponseEntity.status(HttpStatus.OK).body(presp);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Exception occured while getting all project");
        }
        
        

    }
}

