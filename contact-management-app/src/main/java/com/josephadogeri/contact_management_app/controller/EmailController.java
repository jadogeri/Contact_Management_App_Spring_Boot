package com.josephadogeri.contact_management_app.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Hidden
@RestController("/api/v1/email")
public class EmailController {

    //@Autowired
   // private EmailService emailService;

    @GetMapping("/send-welcome-email")
    public String sendWelcomeEmail() throws MessagingException, IOException {
//        Map<String, Object> model = new HashMap<>();
//        model.put("name", "John Doe");
//        model.put("username", "johndoe");
//
//        emailService.sendWelcomeEmail("john.doe@example.com", "Welcome to Our Service!", model);
//        return "Welcome email sent!";

        System.out.println("send welcome email");
        return "welcome email sent";
    }

    @GetMapping("/send-forgot-passowrd")
    public String sendForgotPassword() throws MessagingException, IOException {
//        Map<String, Object> model = new HashMap<>();
//        model.put("name", "John Doe");
//        model.put("username", "johndoe");
//
//        emailService.sendWelcomeEmail("john.doe@example.com", "Welcome to Our Service!", model);
//        return "Welcome email sent!";

        System.out.println("send welcome email");
        return "welcome email sent";
    }

    @GetMapping("/send-reset-password")
    public String sendResetPassword() throws MessagingException, IOException {
//        Map<String, Object> model = new HashMap<>();
//        model.put("name", "John Doe");
//        model.put("username", "johndoe");
//
//        emailService.sendWelcomeEmail("john.doe@example.com", "Welcome to Our Service!", model);
//        return "Welcome email sent!";

        System.out.println("send welcome email");
        return "welcome email sent";
    }

    @GetMapping("/send-deactivate-account")
    public String sendDeactivateAccount() throws MessagingException, IOException {
//        Map<String, Object> model = new HashMap<>();
//        model.put("name", "John Doe");
//        model.put("username", "johndoe");
//
//        emailService.sendWelcomeEmail("john.doe@example.com", "Welcome to Our Service!", model);
//        return "Welcome email sent!";

        System.out.println("send welcome email");
        return "welcome email sent";
    }
}