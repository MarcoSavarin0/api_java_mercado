package com.example.demo.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    @Lazy
    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain) throws
            ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String username= null;
        String jwt = null;
        if(authorizationHeader != null &&  authorizationHeader.startsWith("Bearer")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUserName(jwt);
        }
        if(username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
            userDetails = this.userDetailsService.loadUserByUsername(username);
            if(jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
                usernamePasswordAuthenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new
                        WebAuthenticationDetailsSource()
                        .buildDetails(httpServletRequest));
                SecurityContextHolder.getContext()
                        .setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
