package com.swagger.p1.repository;

import com.swagger.p1.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UsersRepo  extends JpaRepository<Users,Long>{

     Optional<Users>  findByUserName(String username);
}
