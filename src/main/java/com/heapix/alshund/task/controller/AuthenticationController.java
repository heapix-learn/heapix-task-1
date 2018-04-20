package com.heapix.alshund.task.controller;

import com.heapix.alshund.task.controller.dto.SignInRequest;
import com.heapix.alshund.task.service.UserService;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController(value = "/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest request) {

        Authentication authentication = authenticationManager.authenticate(prepareAuthenticationToken(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new String());
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp() {
        return ResponseEntity.ok(new String());
    }

    private UsernamePasswordAuthenticationToken prepareAuthenticationToken(SignInRequest request) {

        return new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    }
}
