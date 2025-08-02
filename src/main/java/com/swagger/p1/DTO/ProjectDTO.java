package com.swagger.p1.DTO;



import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class ProjectDTO {

    private String name;
    private String description;
    private Long UserId;
    public ProjectDTO(String name, String description, Long userId) {
        this.name = name;
        this.description = description;
        UserId = userId;
    }
    public ProjectDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
   

}
