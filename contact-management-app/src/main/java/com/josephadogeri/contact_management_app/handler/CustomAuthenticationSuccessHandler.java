package com.josephadogeri.contact_management_app.handler;

import com.josephadogeri.contact_management_app.entity.User;
import com.josephadogeri.contact_management_app.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationSuccessHandler {


    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository
    //private static final long LOCK_TIME_DURATION = 15 * 60 * 1000; // 15 minutes in milliseconds

    public void onAuthenticationSuccess(String username) throws MessagingException, IOException {

        User user = userRepository.findByUsername(username); // Find the user

        if (user.getFailedAttempts() > 0) { // Check if already failed login attempts

                user.setFailedAttempts(0);
                user.setLastModifiedDate( LocalDateTime.now());
                userRepository.save(user);
        }
    }
}