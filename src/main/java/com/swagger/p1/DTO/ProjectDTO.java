package com.swagger.p1.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
//@Data
@NoArgsConstructor
public class ProjectDTO {

    private String name;
    private String description;
    private Long userId;
    public ProjectDTO(String name, String description, Long userId) {
        this.name = name;
        this.description = description;
        this.userId = userId;
    }
    public ProjectDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
   

}
