package com.heapix.alshund.task.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface JwtTokenService {

    String generateToken(Authentication authentication);
    Optional<String> getBearerToken(String header);
    Long getUserId(String token);
    boolean isTokenValid(String token);
}
