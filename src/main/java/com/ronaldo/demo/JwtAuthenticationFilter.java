package com.ronaldo.demo;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); //getting auth header. if no header or doesnt start w bearer, skip
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            //header is the label of the token
            filterChain.doFilter(request, response);
            return;
        }
        //extract token
        final String jwt = authHeader.substring((7)); //token
        //extract username(email) from token
        final String userEmail = jwtService.extractUsername(jwt);
        //if user not authenticated yet
        if(userEmail !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            //^no auth, no email, then load user from db
            User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new RuntimeException());
            //validate token
            if(jwtService.isTokenValid(jwt, user)) {
                //extracting user role
                String role = jwtService.extractRole(jwt); //extracting role from tokebn
                //convert role string -> spring authority
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                //build auth object
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, List.of(authority)); //role user or admin

                //set auth in context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request,response);
    }
}
