package com.swagger.p1.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import java.util.Date;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String status;
    private Date dueDate;

    @Override
    public String toString() {
        return "Task{id=" + id + ", title='" + title + "'}";
    }

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="Assign_to_project")
    private Project projectTask;

    @ManyToOne
    @JoinColumn(name="Assigned_to_user")
    private Users assignedTo;
}
