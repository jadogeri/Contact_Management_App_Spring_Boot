package com.josephadogeri.contact_management_app.controllers;

import com.josephadogeri.contact_management_app.Auditable;
import com.josephadogeri.contact_management_app.documentations.UserDocumentation;
import com.josephadogeri.contact_management_app.dtos.request.*;
import com.josephadogeri.contact_management_app.dtos.response.*;
import com.josephadogeri.contact_management_app.repositories.UserRepository;
import com.josephadogeri.contact_management_app.services.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController extends Auditable implements UserDocumentation {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService,UserRepository userRepository) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserRegistrationResponseDTO register(@RequestBody UserRegistrationRequestDTO user) throws MessagingException, IOException {
        System.out.println("Registering User : "+ user.toString());
        return userService.register(user);
    }
    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginRequestDTO user) throws MessagingException, IOException {

        return userService.verify(user);
    }
    @PostMapping("/reset-password")
    public UserResetPasswordResponseDTO resetPassword(@RequestBody UserResetPasswordRequestDTO user) throws MessagingException, IOException {

        return userService.resetPassword(user);    }

    @PostMapping("/forgot-password")
    public UserForgotPasswordResponseDTO forgotPassword(@RequestBody UserForgotPasswordRequestDTO user) throws MessagingException, IOException {

        return userService.forgotPassword(user);
    }

    @DeleteMapping("/deactivate")
    public UserDeactivateResponseDTO deactivate(@RequestBody UserDeactivateRequestDTO userDeactivateRequestDTO) throws MessagingException, IOException {

        return userService.deactivate(userDeactivateRequestDTO);
    }
}