package com.swagger.p1.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;
import com.swagger.p1.DTO.TaskDTO;
import com.swagger.p1.DTO.TaskDtoResponse;
import com.swagger.p1.repository.*;
import com.swagger.p1.Entity.*;

@Service
public class TaskService {

    @Autowired
    TaskRepo trepo;
    @Autowired
    ProjectRepo prepo;
    @Autowired
    UsersRepo urepo;

    public ResponseEntity<?> createTask(TaskDTO tdto){
    
        Project currentProject=tdto.getProjectId()!=null?prepo.findById(tdto.getProjectId()).orElse(null):null;
        if(currentProject==null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Project does not exists");
        }
       
        Users curretnUsers=tdto.getUserId()!=null?urepo.findById(tdto.getUserId()).orElse(null):null;
        if(curretnUsers==null){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exists");
        }
        try {
            Task toCreate=new Task();
            TaskDtoResponse responseTask=new TaskDtoResponse();
            toCreate.setTitle(tdto.getTitle());
            toCreate.setDescription(tdto.getDescription());
            toCreate.setDueDate(tdto.getDueDate());
            toCreate.setStatus(tdto.getStatus());
             responseTask.setTitle(tdto.getTitle());
            responseTask.setDescription(tdto.getDescription());
            responseTask.setDueDate(tdto.getDueDate());
            responseTask.setStatus(tdto.getStatus());
            if(currentProject!=null){
            toCreate.setProjectTask(currentProject);
            responseTask.setProjectId(tdto.getProjectId());
            }
            if(curretnUsers!=null){
            toCreate.setAssignedTo(curretnUsers);
            responseTask.setUserId(tdto.getUserId());
            }

            Task createdTask=trepo.save(toCreate);
            responseTask.setId(createdTask.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseTask);

        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occurred in creating the task");
        }
    }

    public ResponseEntity<?> getAllTask(Long UserId){
        try {
            List<TaskDtoResponse> response=new ArrayList<TaskDtoResponse>();
            List<Task> TaskExist=trepo.findByAssignedTo(UserId);
            for(Task i:TaskExist){
                TaskDtoResponse tdtoresp=new TaskDtoResponse();
                tdtoresp.setId(i.getId());
                tdtoresp.setDescription(i.getDescription());
                tdtoresp.setDueDate(i.getDueDate());
                tdtoresp.setStatus(i.getStatus());
                tdtoresp.setTitle(i.getTitle());
                response.add(tdtoresp);

            }
            if(TaskExist.isEmpty()){
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO such user exist");
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occurred in creating the task");
        }

    }
}
