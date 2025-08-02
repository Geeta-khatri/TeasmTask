package com.swagger.p1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import com.swagger.p1.DTO.UsersDTO;
import com.swagger.p1.DTO.authRequestDTO;
import com.swagger.p1.Entity.Users;
import com.swagger.p1.repository.*;
@Service
public class UsersService {

    @Autowired
    private  UsersRepo urepo;

    public ResponseEntity<String>  registreUser(UsersDTO uu){
        if(uu.getUsername()==null || uu.getUsername().trim().isEmpty()){
            return new ResponseEntity<>("User name can not be empty",HttpStatus.BAD_REQUEST);
        }
        Users existingUsers=urepo.findByUserName(uu.getUsername()).orElse(null);
        if(existingUsers!=null){
        return new ResponseEntity<>("User name  already exists",HttpStatus.CONFLICT);
        }
        else {
            Users registU=new Users();
            registU.setUserName(uu.getUsername());
            registU.setEmail(uu.getEmail());
            registU.setPassword(uu.getPassword());
            try {
                urepo.save(registU);
                return new ResponseEntity<>("User Saved Successfully",HttpStatus.OK);
                
            } catch (Exception e) {
                return new ResponseEntity<>("An error occurred while saving the user",HttpStatus.INTERNAL_SERVER_ERROR);
                
            }
    }
    }
    public ResponseEntity<?> loginUser(authRequestDTO authreq){
        Authentication authentication =auth

    }

   
}
