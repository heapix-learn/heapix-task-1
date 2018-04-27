package com.heapix.alshund.task.controller.listener;

import com.heapix.alshund.task.repository.model.User;
import com.heapix.alshund.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Component
public class SignUpCompleteListener implements ApplicationListener<SignUpCompleteEvent> {

    private final String CONFORMATION_URL = "http://%s:%d/auth/sign-up/confirm/%s";

    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(SignUpCompleteEvent event) {

        try {
            confirmSignUp(event);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void confirmSignUp(SignUpCompleteEvent event) throws MessagingException {

        User user = event.getUser();
        String token = prepareToken(user);
        String conformationUrl = String.format(CONFORMATION_URL, event.getApplicationUrl(), 8095, token); // TODO: port value
        sendMessage(user, conformationUrl);
    }

    private String prepareToken(User user) {

        String token = UUID.randomUUID().toString();
        userService.saveVerificationToken(user, token);
        return token;
    }

    private void sendMessage(User user, String conformationUrl) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(user.getEmail());
        helper.setSubject("Registration Confirm");
        helper.setText(conformationUrl);
        mailSender.send(message);
    }
}
