package com.heapix.alshund.task.filter;

import com.heapix.alshund.task.model.User;
import com.heapix.alshund.task.service.JwtTokenService;
import com.heapix.alshund.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER)).ifPresent(header -> handleHeader(header, request));
        filterChain.doFilter(request, response);
    }

    private void handleHeader(String header, HttpServletRequest request) {

        Optional<String> token = jwtTokenService.getBearerToken(header);
        token.ifPresent(value -> handleToken(value, request));
    }

    private void handleToken(String token, HttpServletRequest request) {

        if (jwtTokenService.isTokenValid(token)) {
            Long userId = jwtTokenService.getUserId(token);
            User user = userService.loadById(userId);
            UsernamePasswordAuthenticationToken authenticationToken = createAuthenticationToken(user);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(User user) {

        return new UsernamePasswordAuthenticationToken(user, user.isCredentialsNonExpired(), user.getAuthorities());
    }
}
