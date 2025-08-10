package com.swagger.p1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.swagger.p1.security.CustomUserDetails;
import com.swagger.p1.Entity.Users;
import com.swagger.p1.repository.UsersRepo;

@Service
public class CustonUserDetailService  implements UserDetailsService{

    @Autowired
    private UsersRepo urepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=urepo.findByUserName(username)
        .orElseThrow(()->new UsernameNotFoundException("User not found with username: " + username));
        return new CustomUserDetails(user);
    }
    

}
