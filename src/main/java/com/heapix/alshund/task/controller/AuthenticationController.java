package com.heapix.alshund.task.controller;

import com.heapix.alshund.task.controller.dto.SignInResponse;
import com.heapix.alshund.task.controller.dto.SignUpResponse;
import com.heapix.alshund.task.controller.dto.SignInRequest;
import com.heapix.alshund.task.controller.dto.SignUpRequest;
import com.heapix.alshund.task.service.JwtTokenService;
import com.heapix.alshund.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest request) {

        Authentication authentication = authenticationManager.authenticate(prepareAuthenticationToken(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new SignInResponse(jwtTokenService.generateToken(authentication)));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest request) {

        if (userService.existByUsername(request.getUsername())) {
            return prepareBadResponse("Username is already exist");
        }

        if (userService.existByEmail(request.getEmail())) {
            return prepareBadResponse("Email is already exist");
        }



        return ResponseEntity.ok("asd");
    }

    private UsernamePasswordAuthenticationToken prepareAuthenticationToken(SignInRequest request) {

        return new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    }

    private ResponseEntity<?> prepareBadResponse(String message) {

        return new ResponseEntity(new SignUpResponse(message, false), HttpStatus.BAD_REQUEST);
    }
}
