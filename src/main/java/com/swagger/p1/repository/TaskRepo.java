package com.swagger.p1.repository;

import com.swagger.p1.Entity.Task;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepo extends JpaRepository<Task,Long> {

    //Optional<Task> findByAssignedTo(Long  id);
    @Query("select t from Task t where t.assignedTo.id=:userId")
    List<Task> findByAssignedTo(@Param("userId")Long UserId);
}
