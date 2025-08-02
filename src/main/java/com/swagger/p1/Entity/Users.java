package com.swagger.p1.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import jakarta.persistence.Entity;

@Entity//(tableName="User")
@Data
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String email;
    private String password;

    @OneToMany(mappedBy="ProjectOwner",cascade=CascadeType.ALL)
    private List<Project> UserProject;

    @OneToMany(mappedBy = "assignedTo",cascade =CascadeType.ALL)
    private List<Task> AssignedTask;
}
