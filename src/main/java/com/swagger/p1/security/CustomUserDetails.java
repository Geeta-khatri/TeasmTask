package com.swagger.p1.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.swagger.p1.Entity.*;

public class CustomUserDetails implements UserDetails{

    private final Users user;

    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        //return Collections.emptyList();
    	return Collections.singletonList(new SimpleGrantedAuthority(user.getAssigned_role()));
    }

    public CustomUserDetails(Users user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
     @Override
    public String getUsername() {
        return user.getUserName();  // assuming getUserName() is your field
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Users getUser() {
        return user;
    }

}
