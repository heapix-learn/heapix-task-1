package com.heapix.alshund.task.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface JwtTokenService {

    Optional<String> getBearerToken(String header);
    Long getUserId(String token);
    boolean isTokenValid(String token);
}
