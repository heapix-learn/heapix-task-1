package com.heapix.alshund.task.service;

import java.util.Optional;

public interface JwtTokenService {

    Optional<String> getBearerToken(String header);
    Long getUserId(String token);
    boolean isTokenValid(String token);
}
