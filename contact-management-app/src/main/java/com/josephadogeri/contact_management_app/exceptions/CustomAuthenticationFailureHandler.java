package com.josephadogeri.contact_management_app.exceptions;

import com.josephadogeri.contact_management_app.dto.request.EmailRequest;
import com.josephadogeri.contact_management_app.entity.User;
import com.josephadogeri.contact_management_app.repository.UserRepository;
import com.josephadogeri.contact_management_app.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationFailureHandler {


    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository

    @Autowired
    private EmailService emailService;

    private static final int MAX_FAILED_ATTEMPTS = 3; // Define your limit
    private static final long LOCK_TIME_DURATION = 15 * 60 * 1000; // 15 minutes in milliseconds

    public void onAuthenticationFailure(String username) throws MessagingException, IOException {

        EmailRequest emailRequest = new EmailRequest();


        User user = userRepository.findByUsername(username); // Find the user
        System.out.println("trying to forgot password for username " + username);


        if (user != null) {
            if (user.isEnabled()) { // Check if not already locked
                if (user.getFailedAttempts() < MAX_FAILED_ATTEMPTS - 1) {
                    System.out.println("if : Failed login attempt for  " + username + " is "+ user.getFailedAttempts());

                    user.setFailedAttempts(user.getFailedAttempts() + 1);
                    user.setLastModifiedDate( LocalDateTime.now());
                    userRepository.save(user);
                    throw new BadRequestException("failed login attepts");
                } else {
                    System.out.println("else : Failed login attempt for  " + username + " is "+ user.getFailedAttempts());
                    user.setFailedAttempts(user.getFailedAttempts() + 1);
                    user.setEnabled(false);
                    user.setLastModifiedDate( LocalDateTime.now());
                    userRepository.save(user);
                    // Optionally, send an email to the user about account lockout
                    emailRequest.setTo(user.getEmail());
                    emailRequest.setSubject("Locked Account");
                    Map<String,Object> context = new HashMap<>();
                    context.put("username", user.getUsername());
                    context.put("email", user.getEmail());
                    context.put("companyName", "Spring Boot");
                    context.put("logoUrl", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/1200px-Spring_Framework_Logo_2018.svg.png");
                    context.put("year", String.valueOf(LocalDate.now().getYear()));
                    emailService.sendLockedAccountEmail(emailRequest, context);
                    throw new AccountLockedException("accoutn is llooooooooooooced");

                }
            }else{
                System.out.println("Account is  finally locked");
                emailRequest.setTo(user.getEmail());
                emailRequest.setSubject("Account Currently Locked");
                Map<String,Object> context = new HashMap<>();
                context.put("username", user.getUsername());
                context.put("email", user.getEmail());
                context.put("year", String.valueOf(LocalDate.now().getYear()));
                context.put("companyName", "Spring Boot");
                context.put("logoUrl", "/static/images/logo.png");
                //emailService.sendLckedAccountEmail(emailRequest, context);
                throw new AccountLockedException("accoutn is cuuuuuurentlllllllly llooooooooooooced");

            }
        }
    }
}