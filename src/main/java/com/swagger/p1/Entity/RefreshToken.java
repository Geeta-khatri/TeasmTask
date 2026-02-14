package com.swagger.p1.Entity;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class RefreshToken {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	 
	@OneToOne
	@JoinColumn(name="user_id")
	private Users users;
	
	@Column(unique=true)
	private String tokenMsg;
	
	
	private long RefreshTokenExpiration;
	
	
}
