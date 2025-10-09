package com.josephadogeri.contact_management_app.service;


import com.josephadogeri.contact_management_app.dto.request.EmailRequest;
import com.josephadogeri.contact_management_app.dto.request.UserRegistrationRequestDTO;
import com.josephadogeri.contact_management_app.dto.response.UserRegistrationResponseDTO;
import com.josephadogeri.contact_management_app.entity.User;
import com.josephadogeri.contact_management_app.exceptions.CustomAuthenticationFailureHandler;
import com.josephadogeri.contact_management_app.repository.UserRepository;
import com.josephadogeri.contact_management_app.utils.CredentialsValidatorUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    private CredentialsValidatorUtil credentialsValidatorUtil;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.credentialsValidatorUtil = new CredentialsValidatorUtil();
    }

    public UserRegistrationResponseDTO register(UserRegistrationRequestDTO userRegistrationRequestDTO) throws MessagingException, IOException {
        String username = userRegistrationRequestDTO.getUsername();
        String password = userRegistrationRequestDTO.getPassword();
        String email = userRegistrationRequestDTO.getEmail();

        System.out.println("regitering a new user******************************");

        if(email == null || username == null || password == null){
            throw new IllegalArgumentException("Username, email and password are required");
        }
        //Validate credentials
        if(!credentialsValidatorUtil.isValidEmail(email)){
            throw new IllegalArgumentException("Invalid email address");
        }
        if(!credentialsValidatorUtil.isValidUsername(username)){
            throw new IllegalArgumentException("Invalid username");
        }
        if(!credentialsValidatorUtil.isValidPassword(password)){
            throw new IllegalArgumentException("Invalid password");
        }

        User user = new User();
        user.setUsername(userRegistrationRequestDTO.getUsername());
        user.setEmail(userRegistrationRequestDTO.getEmail());
        String encodedPassword = bCryptPasswordEncoder.encode(userRegistrationRequestDTO.getPassword());
        System.out.println("encoded password*******************************");
        user.setPassword(encodedPassword);

        System.out.println("Response from other endpoint: " );
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(user.getEmail());
        emailRequest.setSubject("Welcome New User");
        context = new HashMap<>();
        context.put("username", user.getUsername());
        context.put("email", user.getEmail());

        emailService.sendWelcomeEmail(emailRequest, context);
        //return user;
        User savedUser =  userRepository.save(user);
        UserRegistrationResponseDTO userRegistrationResponseDTO
                = new UserRegistrationResponseDTO(savedUser.getUsername(), savedUser.getEmail());
        userRegistrationResponseDTO.setCreatedAt(savedUser.getCreatedAt());
        userRegistrationResponseDTO.setLastModifiedDate(savedUser.getLastModifiedDate());
        return userRegistrationResponseDTO;

    }

    public String verify(User user) throws MessagingException, IOException {
        try{
            Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                )
            );
            //User u =userRepository.findByUsername(user.getUsername());
            if (authenticate.isAuthenticated()) {
                return jwtService.generateToken(user);
            }else{
                System.out.println("invalid password");
                return "fail";
            }
        } catch (BadCredentialsException e) {
            // Handle invalid password specifically
            customAuthenticationFailureHandler.onAuthenticationFailure(user.getUsername());
            return ("resting data Invalid username or password");
        } catch (AuthenticationException e) {
            // Handle other authentication errors
            return ("Authentication failed");
        }
    }
}


/*
*
*    public String verify(UserLoginRequestDTO userLoginRequestDTO) {
//        try{
            String username = userLoginRequestDTO.getUsername();
            System.out.println("username : " + username);
            System.out.println("password : " + userLoginRequestDTO.getPassword());
            String password = userLoginRequestDTO.getPassword();

            if(username == null || password == null){
                throw new IllegalArgumentException("Username, email and password are required");
            }
            //Validate credentials
            if(!credentialsValidatorUtil.isValidUsername(username)){
                throw new IllegalArgumentException("Invalid username");
            }
            if(!credentialsValidatorUtil.isValidPassword(password)){
                throw new IllegalArgumentException("Invalid password");
            }

            User user = new User();
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
//                        user.getUsername(), user.getPassword()
                        username, password

                )
        );
        //User u =userRepository.findByUsername(user.getUsername());
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(user);
        }else{
            System.out.println("invalid password");
            return "fail";
        }
//        } catch (BadCredentialsException e) {
//            // Handle invalid password specifically
//            return ("resting data Invalid username or password");
//        } catch (AuthenticationException e) {
//            // Handle other authentication errors
//            return ("Authentication failed");
//        }
    }*/