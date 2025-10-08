package com.josephadogeri.contact_management_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        System.out.println("CustomAuthenticationEntryPoint");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        } else if (authentication != null) {
            // For other types of principals (e.g., simple String principal)
            username = authentication.getName();
        }

        System.out.println("request :" + request.getRequestURI()  );
        System.out.println("Failed login attempt for user: " + username + ". Reason: " + authException.getMessage());


        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", System.currentTimeMillis());
        errorDetails.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        errorDetails.put("error", "Unauthorized");
        errorDetails.put("message", "Invalid username or password."); // Customize this message
        errorDetails.put("path", request.getRequestURI());

        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }
}


/*
*
*

* //        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));


        // Log the exception for debugging
        System.err.println("line 43 Authentication failed: " + authException.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));

        // Handle specific exceptions or provide a general unauthorized response
        if (authException instanceof UsernameNotFoundException) {
            //response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
        } else if (authException instanceof BadCredentialsException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");
        } else if (authException instanceof AccountExpiredException ||
                authException instanceof LockedException ||
                authException instanceof DisabledException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Account is " + authException.getMessage().toLowerCase());
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication required");
        }*/