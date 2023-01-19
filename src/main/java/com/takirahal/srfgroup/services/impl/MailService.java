package com.takirahal.srfgroup.services.impl;

import com.takirahal.srfgroup.modules.user.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Value("${dynamicsvariables.base-url-frontend}")
    private String BASE_URL;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MessageSource messageSource;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.info(
                "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart,
                isHtml,
                to,
                subject,
                content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo("srfgroup.contact@gmail.com"); // Just for mock: To be removed
            // message.setTo(to);
            message.setFrom("srfgroup.contact@gmail.com");
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            log.error("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey, boolean withLink) {
        if (user.getEmail() == null) {
            log.error("Email doesn't exist for user '{}'", user.getEmail());
            return;
        }
        Locale locale = Locale.forLanguageTag(!user.getLangKey().equals("") ? user.getLangKey() : "fr");
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("baseUrl", BASE_URL);
        context.setVariable("titleKey", titleKey);
        context.setVariable("withLink", withLink);
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        log.debug("subject = '{}'", subject);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user, boolean withLink) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title", withLink);
    }

    @Async
    public void sendPasswordResetMail(User user, boolean withLink) {
        log.info("Sending password reset email to '{}' {}", user.getEmail(), withLink);
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title", withLink);
    }
}
