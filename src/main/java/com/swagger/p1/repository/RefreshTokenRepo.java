package com.swagger.p1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swagger.p1.Entity.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

	@Query(value="select * from refresh_token where token_msg=:token_msg",nativeQuery=true)
	Optional<RefreshToken> findByTokenMsg(@Param("token_msg")String tokenMsg);
}
