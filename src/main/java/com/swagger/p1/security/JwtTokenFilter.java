package com.swagger.p1.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.swagger.p1.Service.CustonUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{

    public JwtTokenFilter() {
    }
    
    @Autowired
    private CustonUserDetailService custonUserDetailService;

    @Autowired
    private JWTConfig   jwtconfig;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        System.out.println("Checking skip for path: " + request.getRequestURI());
        return "/UsersInfo/login".equals(path) || "/UsersInfo/register".equals(path)  ; // skip filter for this path
    }
    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse resp,FilterChain chain) 
    throws IOException,ServletException{

        //String path=req.getRequestURI();

        //HttpServletRequest request=(HttpServletRequest)req;
        String authHeader=req.getHeader("Authorization");
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            String jwt=authHeader.substring(7);
            String username=jwtconfig.validateToken(jwt);
            String role=jwtconfig.extractRole(jwt);
            if(username==null){
                ((HttpServletResponse)resp).sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid Token");
                return;
            }
            UserDetails uDetsils=custonUserDetailService.loadUserByUsername(username);
            if(SecurityContextHolder.getContext().getAuthentication()==null){
                //UsernamePasswordAuthenticationToken authToken =new UsernamePasswordAuthenticationToken(uDetsils,null,Collections.emptyList());
            	UsernamePasswordAuthenticationToken authToken =new UsernamePasswordAuthenticationToken(uDetsils,null,Collections.singletonList(new SimpleGrantedAuthority(role)));
            	SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        chain.doFilter(req, resp);
        
    }

    

}
