package com.swagger.p1.repository;

import com.swagger.p1.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface ProjectRepo  extends JpaRepository<Project,Long>{

    Optional<Project> findByName(String name);
    
    @Query(value="select * from project where asign_to_user=:id",nativeQuery=true)// nativeQuery=true or else will get error for *
    List<Project> findByProjectOwnerId(@Param("id") Long id);
}
