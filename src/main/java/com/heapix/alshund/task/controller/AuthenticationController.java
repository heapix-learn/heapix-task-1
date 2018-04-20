package com.heapix.alshund.task.controller;

import com.heapix.alshund.task.controller.dto.SignInDto;
import com.heapix.alshund.task.controller.dto.UserDto;
import com.heapix.alshund.task.controller.listener.SignUpCompleteEvent;
import com.heapix.alshund.task.controller.response.SignInResponse;
import com.heapix.alshund.task.controller.response.SignUpResponse;
import com.heapix.alshund.task.model.User;
import com.heapix.alshund.task.service.JwtTokenService;
import com.heapix.alshund.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInDto signInDto) {

        Authentication authentication = authenticationManager.authenticate(prepareAuthenticationToken(signInDto));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new SignInResponse(jwtTokenService.generateToken(authentication)));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDto userDto, HttpServletRequest request) {

        if (userService.existByUsername(userDto.getUsername())) {
            return prepareBadResponse("Username is already exist");
        }

        if (userService.existByEmail(userDto.getEmail())) {
            return prepareBadResponse("Email is already exist");
        }

        //SignUp with mapper

//        eventPublisher.publishEvent(new SignUpCompleteEvent(user, request.getContextPath()));

        return ResponseEntity.ok("asd");
    }

    @GetMapping("/sign-up/confirm/{token}")
    public ResponseEntity<?> confirmSignUp(@RequestParam("token") String token) {


        return ResponseEntity.ok("confirm");
    }

    private UsernamePasswordAuthenticationToken prepareAuthenticationToken(SignInDto request) {

        return new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    }

    private ResponseEntity<?> prepareBadResponse(String message) {

        return new ResponseEntity(new SignUpResponse(message, false), HttpStatus.BAD_REQUEST);
    }
}
