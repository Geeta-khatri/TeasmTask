package com.swagger.p1.repository;

import com.swagger.p1.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ProjectRepo  extends JpaRepository<Project,Long>{

    Optional<Project> findByName(String name);
}
