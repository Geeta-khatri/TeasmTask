package com.swagger.p1.DTO;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
public class TaskDtoResponse {
private String title;
    private Long id;
    private String description;
    private String status;
    private Date dueDate;
    private Long projectId;
    private Long userId;
}
