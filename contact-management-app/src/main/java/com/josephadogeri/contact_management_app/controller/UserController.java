package com.josephadogeri.contact_management_app.controller;



import com.josephadogeri.contact_management_app.Auditable;
import com.josephadogeri.contact_management_app.documentation.UserDocumentation;
import com.josephadogeri.contact_management_app.dto.request.UserRegistrationRequestDTO;
import com.josephadogeri.contact_management_app.dto.response.UserRegistrationResponseDTO;
import com.josephadogeri.contact_management_app.entity.User;
import com.josephadogeri.contact_management_app.repository.UserRepository;
import com.josephadogeri.contact_management_app.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController extends Auditable implements UserDocumentation {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserService userService;

    public UserController(UserService userService,UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public UserRegistrationResponseDTO register(@RequestBody UserRegistrationRequestDTO user) throws MessagingException, IOException {
        System.out.println("Registering User : "+ user.toString());
        return userService.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody User user){

        return userService.verify(user);
    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody User user){

        return "reset password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody User user){

        return "forgot password";
    }

    @PostMapping("/deactivate")
    public String deactivate(@RequestBody User user){

        return "deactivate account";
    }
}