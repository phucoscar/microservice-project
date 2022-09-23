package com.dtsgroup.userservice.config.filter;

import com.dtsgroup.userservice.entity.User;
import com.dtsgroup.userservice.exception.BusinessException;
import com.dtsgroup.userservice.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!hasAuthorizationHeader(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            String accessToken = getAccessToken(request);
            if (!jwtUtil.validateJwtToken(accessToken)) {
                filterChain.doFilter(request, response);
                return;
            }
            setAuthenticationContext(accessToken, request);
        } catch (BusinessException e) {
            System.out.println(e);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationContext(String accessToken, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(accessToken);
        UsernamePasswordAuthenticationToken authentication = new
                UsernamePasswordAuthenticationToken(userDetails, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String accessToken) {
        com.dtsgroup.userservice.entity.User userDetails = new User();
        String subject = jwtUtil.getSubject(accessToken);
        userDetails.setUsername(subject);
        return userDetails;
    }

    private boolean hasAuthorizationHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(header))
            return false;
        return true;
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
