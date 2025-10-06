package com.josephadogeri.contact_management_app.controller;



import com.josephadogeri.contact_management_app.Auditable;
import com.josephadogeri.contact_management_app.dto.request.UserRegistrationRequestDTO;
import com.josephadogeri.contact_management_app.dto.response.UserRegistrationResponseDTO;
import com.josephadogeri.contact_management_app.entity.User;
import com.josephadogeri.contact_management_app.repository.UserRepository;
import com.josephadogeri.contact_management_app.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController extends Auditable {

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
        return userService.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody User user){

        return userService.verify(user);
    }
}