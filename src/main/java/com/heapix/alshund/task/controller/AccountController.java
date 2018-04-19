package com.heapix.alshund.task.controller;

import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/")
public class AccountController {

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn() {
        return ResponseEntity.ok(new String());
    }
}
