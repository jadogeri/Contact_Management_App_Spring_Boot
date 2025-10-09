package com.josephadogeri.contact_management_app.exceptions;

/**
 * @author Joseph Adogeri
 * @since 25-SEP-2025
 * @version 1.0.0
 */

import com.josephadogeri.contact_management_app.utils.RequestContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<Map<String, String>> handleAccountLockedException(AccountLockedException ex) {
        HttpServletRequest request = RequestContextUtil.getCurrentHttpRequest();
        System.out.println("account locked"+ex.getMessage());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println("username==========    "+authentication.getName()); // Returns the authenticated username
        }

        Map<String, String> errorDetails = new HashMap<>();

        errorDetails.put("message", ex.getMessage());
        errorDetails.put("code", "ACCOUNT_LOCKED");
        errorDetails.put("url", request != null ? request.getRequestURI() : "" );
        errorDetails.put("method", request != null ? request.getMethod() : "");
        errorDetails.put("status", String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));

        errorDetails.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException ex) {
        HttpServletRequest request = RequestContextUtil.getCurrentHttpRequest();
        System.out.println("username or password invalid"+ex.getMessage());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println("username==========    "+authentication.getName()); // Returns the authenticated username
        }

        Map<String, String> errorDetails = new HashMap<>();

        errorDetails.put("message", ex.getMessage());
        errorDetails.put("code", "BAD_CREDENTIALS");
        errorDetails.put("url", request != null ? request.getRequestURI() : "" );
        errorDetails.put("method", request != null ? request.getMethod() : "");
        errorDetails.put("status", String.valueOf(HttpServletResponse.SC_BAD_REQUEST));

        errorDetails.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        HttpServletRequest request = RequestContextUtil.getCurrentHttpRequest();
        System.out.println("username not found exception"+ex.getMessage());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                System.out.println("username==========    "+authentication.getName()); // Returns the authenticated username
            }

        Map<String, String> errorDetails = new HashMap<>();

        errorDetails.put("message", ex.getMessage());
        errorDetails.put("code", "USERNAME_NOT_FOUND");
        errorDetails.put("url", request != null ? request.getRequestURI() : "" );
        errorDetails.put("method", request != null ? request.getMethod() : "");
        errorDetails.put("status", String.valueOf(HttpServletResponse.SC_NOT_FOUND));
        errorDetails.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        HttpServletRequest request = RequestContextUtil.getCurrentHttpRequest();

        Map<String, String> errorDetails = new HashMap<>();

        errorDetails.put("message", ex.getMessage());
        errorDetails.put("code", "RESOURCE_NOT_FOUND");
        errorDetails.put("url", request != null ? request.getRequestURI() : "" );
        errorDetails.put("method", request != null ? request.getMethod() : "");
        errorDetails.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>("Invalid argument: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class) // Handles a specific custom exception
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        HttpServletRequest request = RequestContextUtil.getCurrentHttpRequest();

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("code", "NOT_FOUND");
        errorDetails.put("url", request != null ? request.getRequestURI() : "" );
        errorDetails.put("method", request != null ? request.getMethod() : "");
        errorDetails.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceConflictException.class) // Handles conflict exception
    public ResponseEntity<Map<String, String>> handleConflictException(ResourceConflictException ex) {
        HttpServletRequest request = RequestContextUtil.getCurrentHttpRequest();

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("code", "CONFLICT");
        errorDetails.put("url", request != null ? request.getRequestURI() : "" );
        errorDetails.put("method", request != null ? request.getMethod() : "");
        errorDetails.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class) // Handles bad request exception
    public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestException ex) {
        System.out.println("Global exception handler ");
        HttpServletRequest request = RequestContextUtil.getCurrentHttpRequest();

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("code", "BAD_REQUEST");
        errorDetails.put("url", request != null ? request.getRequestURI() : "" );
        errorDetails.put("method", request != null ? request.getMethod() : "");
        errorDetails.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class) // Handles all other generic exceptions
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        HttpServletRequest request = RequestContextUtil.getCurrentHttpRequest();

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", "An unexpected error occurred.");
        errorDetails.put("code", "INTERNAL_SERVER_ERROR");
        errorDetails.put("url", request != null ? request.getRequestURI() : "" );
        errorDetails.put("method", request != null ? request.getMethod() : "");
        errorDetails.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
