package com.josephadogeri.contact_management_app.services;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.josephadogeri.contact_management_app.dtos.request.EmailRequest;
import com.josephadogeri.contact_management_app.entities.User;
import com.josephadogeri.contact_management_app.utils.EmailUtil;
import com.josephadogeri.contact_management_app.utils.TemplateNameUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private EmailRequest emailRequest ;
    private Map<String,Object> context ;

    @Autowired
    private final JavaMailSender mailSender;

    @Autowired
    private final Handlebars handlebars; // Injected by handlebars-spring-boot-starter

    private final MimeMessage mimeMessage;

    private final MimeMessageHelper mimeMessageHelper;

    public  EmailService(Handlebars handlebars, JavaMailSender javaMailSender) throws MessagingException {
        this.handlebars = handlebars;
        this.mailSender = javaMailSender;
        this.mimeMessage = mailSender.createMimeMessage();
        this.mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        this.context = new HashMap<>();
        this.emailRequest = new EmailRequest();


        context.put("year", EmailUtil.YEAR);
        context.put("companyName", EmailUtil.COMPANY_NAME);
        context.put("logoUrl", EmailUtil.LOGO_URL);
    }

    private String generateEmailContent(String templateName, Map<String, Object> context) throws IOException {
        Template template = handlebars.compile(templateName);
        return template.apply(context);
    }

    private void sendTemplatedEmail(EmailRequest emailRequest, String htmlContent, Map<String, Object> context) throws MessagingException, IOException {

        mimeMessageHelper.setTo(emailRequest.getTo());
        mimeMessageHelper.setSubject(emailRequest.getSubject());
        mimeMessageHelper.setText(htmlContent, true); // true indicates HTML content
        mailSender.send(mimeMessage);
    }

    public void sendWelcomeEmail(User user) throws MessagingException, IOException {
        try {
            emailRequest.setTo(user.getEmail());
            emailRequest.setSubject("Welcome to " + EmailUtil.COMPANY_NAME + ", " + user.getUsername());
            context.put("username", user.getUsername());
            context.put("email", user.getEmail());

            String htmlContent = generateEmailContent(TemplateNameUtil.WELCOME_TEMPLATE_NAME, context);
            System.out.println("before sending email............................................");
            sendTemplatedEmail(emailRequest, htmlContent, context);
        } catch (Exception e) {
            System.out.println("Email sending failed............................................");
            e.printStackTrace();
            System.out.println("Error: "+ e.getMessage());
        }

    }

    public void sendResetPasswordEmail(User user) throws MessagingException, IOException {

        emailRequest.setTo(user.getEmail());
        emailRequest.setSubject("Succesully reset your password");
        context.put("username", user.getUsername());
        context.put("email", user.getEmail());

        String htmlContent = generateEmailContent(TemplateNameUtil.RESET_PASSWORD_TEMPLATE_NAME, context);
        sendTemplatedEmail(emailRequest, htmlContent, context);

    }

    public void sendForgotPasswordEmail(User user, String temporaryPassword) throws MessagingException, IOException {

        emailRequest.setTo(user.getEmail());
        emailRequest.setSubject("Forgot Password");
        context.put("username", user.getUsername());
        context.put("email", user.getEmail());
        context.put("temporaryPassword", temporaryPassword);

        String htmlContent = generateEmailContent(TemplateNameUtil.FORGOT_PASSWORD_TEMPLATE_NAME, context);
        sendTemplatedEmail(emailRequest, htmlContent, context);

    }

    public void sendDeactivatedAccountEmail(User user) throws MessagingException, IOException {

        emailRequest.setTo(user.getEmail());
        emailRequest.setSubject("Deactivate account, " + user.getUsername());
        context.put("username", user.getUsername());
        context.put("email", user.getEmail());
        String htmlContent = generateEmailContent(TemplateNameUtil.ACCOUNT_DEACTIVATION_TEMPLATE_NAME, context);
        sendTemplatedEmail(emailRequest, htmlContent, context);

    }

    public void sendLockedAccountEmail(User user) throws MessagingException, IOException {
        emailRequest.setTo(user.getEmail());
        emailRequest.setSubject("Locked Account");
        context.put("username", user.getUsername());
        context.put("email", user.getEmail());

        String htmlContent = generateEmailContent(TemplateNameUtil.ACCOUNT_LOCKED_TEMPLATE_NAME, context);
        sendTemplatedEmail(emailRequest, htmlContent, context);

    }

}