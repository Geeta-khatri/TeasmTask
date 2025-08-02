package com.swagger.p1.Entity;



import jakarta.persistence.Column;

import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import jakarta.persistence.Entity;
@Data
@NoArgsConstructor
@Entity//(tableName="Projects")
public class Project {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ProjectName")
    private String name;

    private String description;

   @ManyToOne
   @JoinColumn(name="Asign_to_user")
    private Users ProjectOwner;

    @OneToMany(mappedBy = "projectTask", cascade = CascadeType.ALL)
    private List<Task> Projecttask;


}
