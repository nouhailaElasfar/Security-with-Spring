package com.example.security.filters;

import com.example.security.utils.JwUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class JwTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwUtil jwUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader=httpServletRequest.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isEmpty() ||
        !authorizationHeader.startsWith("Bearer")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        final String token=authorizationHeader.split(" ")[1].trim();
        if(!jwUtil.validate(token)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        String username= jwUtil.getUsername(token);

    }

}
