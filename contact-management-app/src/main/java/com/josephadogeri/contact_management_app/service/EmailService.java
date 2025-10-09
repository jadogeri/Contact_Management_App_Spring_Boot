package com.josephadogeri.contact_management_app.service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.josephadogeri.contact_management_app.dto.request.EmailRequest;
import com.josephadogeri.contact_management_app.utils.TemplateNameUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class EmailService {

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

    public void sendWelcomeEmail(EmailRequest emailRequest, Map<String, Object> context) throws MessagingException, IOException {

        String htmlContent = generateEmailContent(TemplateNameUtil.WELCOME_TEMPLATE_NAME, context);
        sendTemplatedEmail(emailRequest, htmlContent, context);

    }

    public void sendResetPasswordEmail(EmailRequest emailRequest, Map<String, Object> context) throws MessagingException, IOException {

        String htmlContent = generateEmailContent(TemplateNameUtil.RESET_PASSWORD_TEMPLATE_NAME, context);
        sendTemplatedEmail(emailRequest, htmlContent, context);

    }

    public void sendForgotPasswordEmail(EmailRequest emailRequest, Map<String, Object> context) throws MessagingException, IOException {

        String htmlContent = generateEmailContent(TemplateNameUtil.FORGOT_PASSWORD_TEMPLATE_NAME, context);
        sendTemplatedEmail(emailRequest, htmlContent, context);

    }

    public void sendDeactivateAccountEmail(EmailRequest emailRequest, Map<String, Object> context) throws MessagingException, IOException {

        String htmlContent = generateEmailContent(TemplateNameUtil.DEACTIVATE_TEMPLATE_NAME, context);
        sendTemplatedEmail(emailRequest, htmlContent, context);

    }

    public void sendLckedAccountEmail(EmailRequest emailRequest, Map<String, Object> context) throws MessagingException, IOException {

        String htmlContent = generateEmailContent(TemplateNameUtil.ACCOUNT_LOCKED_TEMPLATE_NAME, context);
        sendTemplatedEmail(emailRequest, htmlContent, context);

    }

}