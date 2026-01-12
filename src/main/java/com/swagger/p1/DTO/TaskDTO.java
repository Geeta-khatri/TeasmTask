package com.swagger.p1.DTO;

import java.time.LocalDate;
import java.util.Date;



import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TaskDTO {

    private String title;
    private String description;
    private String status;
    //private Date dueDate;
    private LocalDate dueDate;
    private Long projectId;
    private Long userId;
    public TaskDTO(String title, String description, String status, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }
    public TaskDTO(String title, String description, String status, LocalDate dueDate, Long projectId, Long userId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.projectId = projectId;
        this.userId = userId;
    }
    public TaskDTO(String title, String description, String status, LocalDate dueDate, Long projectId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.projectId = projectId;
    }
   
    

}
