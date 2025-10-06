package com.josephadogeri.contact_management_app.service;


import com.josephadogeri.contact_management_app.dto.request.EmailRequest;
import com.josephadogeri.contact_management_app.entity.User;
import com.josephadogeri.contact_management_app.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private EmailService emailService;
    private Map<String, Object> context;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User register(User user) throws MessagingException, IOException {
        user.setPassword(bCryptPasswordEncoder
                .encode(user.getPassword()));

        System.out.println("Response from other endpoint: " );
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(user.getEmail());
        emailRequest.setSubject("Welcome New User");
        context = new HashMap<>();
        context.put("username", user.getUsername());
        context.put("email", user.getEmail());

        emailService.sendWelcomeEmail(emailRequest, context);
        return user;
        //return userRepository.save(user);
    }

    public String verify(User user) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                )
        );
        //User u =userRepository.findByUsername(user.getUsername());
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(user);
        }else{
            return "fail";
        }
    }
}
