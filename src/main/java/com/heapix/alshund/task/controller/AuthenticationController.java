package com.heapix.alshund.task.controller;

import com.heapix.alshund.task.controller.bo.SignInBo;
import com.heapix.alshund.task.controller.bo.SignUpBo;
import com.heapix.alshund.task.controller.converter.UserConverter;
import com.heapix.alshund.task.controller.dto.SignInDto;
import com.heapix.alshund.task.controller.dto.UserDto;
import com.heapix.alshund.task.controller.listener.SignUpCompleteEvent;
import com.heapix.alshund.task.repository.model.User;
import com.heapix.alshund.task.service.JwtTokenService;
import com.heapix.alshund.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInDto signInDto) {

        Authentication authentication = authenticationManager.authenticate(prepareAuthenticationToken(signInDto));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new SignInBo(jwtTokenService.generateToken(authentication)));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDto userDto) throws UnknownHostException {

        if (userService.existByUsername(userDto.getUsername())) {
            return prepareBadResponse("Username is already exist");
        }

        if (userService.existByEmail(userDto.getEmail())) {
            return prepareBadResponse("Email is already exist");
        }

        User user = userConverter.dtoToUser(userDto);
        userService.saveUser(user);
        eventPublisher.publishEvent(new SignUpCompleteEvent(user, InetAddress.getLocalHost().getHostAddress()));

        return ResponseEntity.ok("asd");
    }

    @GetMapping("/sign-up/confirm/{token}")
    public ResponseEntity<?> confirmSignUp(@PathVariable("token") String token) {

        System.out.println(token);
        return ResponseEntity.ok("confirm");
    }

    private UsernamePasswordAuthenticationToken prepareAuthenticationToken(SignInDto request) {

        return new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    }

    private ResponseEntity<?> prepareBadResponse(String message) {

        return new ResponseEntity(new SignUpBo(message, false), HttpStatus.BAD_REQUEST);
    }
}
