package com.heapix.alshund.task.controller.listener;

import com.heapix.alshund.task.model.User;
import com.heapix.alshund.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

public class SignUpCompleteListener implements ApplicationListener<SignUpCompleteEvent> {

    @Autowired
    private UserService userService;


    @Override
    public void onApplicationEvent(SignUpCompleteEvent event) {

    }

    private void confirmSignUp(SignUpCompleteEvent event) {

        String tokenValue = prepareToken(event.getUser());

    }

    private String prepareToken(User user) {

        String token = UUID.randomUUID().toString();
        userService.saveVerificationToken(user, token);
        return token;
    }
}
