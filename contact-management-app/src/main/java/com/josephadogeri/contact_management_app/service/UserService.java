package com.josephadogeri.contact_management_app.service;


import com.josephadogeri.contact_management_app.dto.request.EmailRequest;
import com.josephadogeri.contact_management_app.dto.request.UserLoginRequestDTO;
import com.josephadogeri.contact_management_app.dto.request.UserRegistrationRequestDTO;
import com.josephadogeri.contact_management_app.dto.request.UserResetPasswordRequestDTO;
import com.josephadogeri.contact_management_app.dto.response.UserRegistrationResponseDTO;
import com.josephadogeri.contact_management_app.entity.User;
import com.josephadogeri.contact_management_app.exceptions.AccountLockedException;
import com.josephadogeri.contact_management_app.exceptions.CustomAuthenticationFailureHandler;
import com.josephadogeri.contact_management_app.exceptions.CustomAuthenticationSuccessHandler;
import com.josephadogeri.contact_management_app.repository.UserRepository;
import com.josephadogeri.contact_management_app.utils.CredentialsValidatorUtil;
import com.josephadogeri.contact_management_app.utils.PasswordGenerator;
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
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;


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
        user.setPassword(encodedPassword);

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

    public String verify(UserLoginRequestDTO userLoginRequestDTO) throws MessagingException, IOException {
        User user = null;

        try{
            user = userRepository.findByUsername(userLoginRequestDTO.getUsername());
            Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestDTO.getUsername(), userLoginRequestDTO.getPassword()
                )
            );
            if(!user.isEnabled()){
                throw new AccountLockedException("Account is locked");
            }




            if (authenticate.isAuthenticated()) {
                customAuthenticationSuccessHandler.onAuthenticationSuccess(user.getUsername());
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

    public String resetPassword(UserResetPasswordRequestDTO userResetPasswordRequestDTO) throws MessagingException, IOException {
        String email = userResetPasswordRequestDTO.getEmail();

        if(email == null ){
            throw new IllegalArgumentException("email is required");
        }
        //Validate credentials
        if(!credentialsValidatorUtil.isValidEmail(email)){
            throw new IllegalArgumentException("Invalid email address");
        }

        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new RuntimeException("user not found by email");
        }
        //generate password and encode it , then save to
        //database and send user the new password to reset account
        String generatedPassword = PasswordGenerator.generateStrongPassword(10);
        System.out.println("genrated password ==" +  generatedPassword);
        //encode password
        String encodedPassword = bCryptPasswordEncoder.encode(generatedPassword);
        //update user password and save to database
        user.setPassword(encodedPassword);
        userRepository.save(user);
        // send new password to user



        return "";
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