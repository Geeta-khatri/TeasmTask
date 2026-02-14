package com.swagger.p1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swagger.p1.Entity.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

}
