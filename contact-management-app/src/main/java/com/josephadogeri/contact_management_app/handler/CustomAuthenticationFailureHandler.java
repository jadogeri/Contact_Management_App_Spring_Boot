package com.josephadogeri.contact_management_app.handler;

import com.josephadogeri.contact_management_app.entity.User;
import com.josephadogeri.contact_management_app.exceptions.AccountLockedException;
import com.josephadogeri.contact_management_app.repository.UserRepository;
import com.josephadogeri.contact_management_app.service.EmailService;
import jakarta.mail.MessagingException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationFailureHandler {


    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository

    @Autowired
    private EmailService emailService;

    private static final int MAX_FAILED_ATTEMPTS = 3; // Define your limit

    public void onAuthenticationFailure(String username) throws MessagingException, IOException {

        User user = userRepository.findByUsername(username); // Find the user
        System.out.println("trying to forgot password for username " + username);


        if (user != null) {
            if (user.isEnabled()) { // Check if not already locked
                if (user.getFailedAttempts() < MAX_FAILED_ATTEMPTS) {
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

                    emailService.sendLockedAccountEmail(user);
                    throw new AccountLockedException("accoutn is llooooooooooooced");

                }
            }else{

                throw new AccountLockedException("accoutn is cuuuuuurentlllllllly llooooooooooooced");

            }
        }
    }
}