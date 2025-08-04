package com.swagger.p1.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{

//     public void setJwtConfig(JWTConfig jwtconfig) {
//     this.jwtconfig = jwtconfig;
// }
    
    public JwtTokenFilter() {
    }
    
    @Autowired
    private JWTConfig   jwtconfig;

    // private final JWTConfig jwtconfig;

    // public JwtTokenFilter(JWTConfig jwtconfig) {
    //     this.jwtconfig = jwtconfig;
    // }
    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse resp,FilterChain chain) 
    throws IOException,ServletException{

        
        //HttpServletRequest request=(HttpServletRequest)req;
        String authHeader=req.getHeader("Authorization");
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            String jwt=authHeader.substring(7);
            String username=jwtconfig.validateToken(jwt);
            if(username==null){
                ((HttpServletResponse)resp).sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid Token");
                return;
            }
            if(SecurityContextHolder.getContext().getAuthentication()==null){
                UsernamePasswordAuthenticationToken authToken =new UsernamePasswordAuthenticationToken(username,null,Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        chain.doFilter(req, resp);
        if (logger.isDebugEnabled()) {
            logger.debug("Completed JwtTokenFilter.doFilterInternal");
        }
    }

    

}
