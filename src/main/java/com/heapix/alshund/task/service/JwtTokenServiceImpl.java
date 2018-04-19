package com.heapix.alshund.task.service;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenServiceImpl.class);

    private static final String BEARER_TOKEN_REGEX = "^(Bearer)(\\s)(?<token>.+)$";
    private static final String TOKEN_GROUP_NAME = "token";

    private Pattern tokenPattern = Pattern.compile(BEARER_TOKEN_REGEX);

    @Value("${auth.token.key}")
    private String signingKey;

    @Override
    public Optional<String> getBearerToken(String header) {

        String token = null;
        Matcher tokenMatcher = tokenPattern.matcher(header);
        if (find(header, tokenMatcher)) {
            token = tokenMatcher.group(TOKEN_GROUP_NAME);
        }
        return Optional.ofNullable(token);
    }

    @Override
    public Long getUserId(String token) {

        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    @Override
    public boolean isTokenValid(String token) {

        try {
            Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {

            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException e) {

            logger.error("Unsupported JWT token");
        } catch (MalformedJwtException e) {

            logger.error("Malformed JWT token");
        } catch (SignatureException e) {

            logger.error("Invalid JWT signature");
        } catch (IllegalArgumentException e) {

            logger.error("JWT claims string is empty");
        }
        return false;
    }

    private boolean find(String header, Matcher tokenMatcher) {

        return header != null && tokenMatcher.find();
    }
}