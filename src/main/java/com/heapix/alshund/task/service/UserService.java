package com.heapix.alshund.task.service;

import com.heapix.alshund.task.repository.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface UserService extends UserDetailsService {

    User loadById(Long id);
    boolean existByUsername(String username);
    boolean existByEmail(String email);
    Long saveUser(User user);
    void saveVerificationToken(User user, String token);
}
