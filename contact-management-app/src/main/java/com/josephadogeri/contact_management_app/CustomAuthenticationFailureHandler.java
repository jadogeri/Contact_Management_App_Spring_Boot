package com.josephadogeri.contact_management_app;

import com.josephadogeri.contact_management_app.entity.User;
import com.josephadogeri.contact_management_app.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
//public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository

    private static final int MAX_FAILED_ATTEMPTS = 3; // Define your limit
    private static final long LOCK_TIME_DURATION = 15 * 60 * 1000; // 15 minutes in milliseconds

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username"); // Or however you get the username


        System.out.println("request :" + request.getRequestURI()  );
        System.out.println("Failed login attempt for user: " + username + ". Reason: " + exception.getMessage());

        User user = userRepository.findByUsername(username); // Find the user
        System.out.println("trying to forgot password for username " + username);

        if (user != null) {
            if (!user.isEnabled()) { // Check if not already locked
                if (user.getFailedAttempts() < MAX_FAILED_ATTEMPTS - 1) {
                    user.setFailedAttempts(user.getFailedAttempts() + 1);
                    user.setLastModifiedDate( LocalDateTime.now());
                    userRepository.save(user);
                } else {
                    user.setEnabled(false);
                    user.setLastModifiedDate( LocalDateTime.now());
                    userRepository.save(user);
                    // Optionally, send an email to the user about account lockout
                }
            }
        }
        super.onAuthenticationFailure(request, response, exception); // Continue with default behavior
    }
}