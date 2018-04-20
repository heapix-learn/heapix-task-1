package com.heapix.alshund.task.service;

import com.heapix.alshund.task.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public interface UserService extends UserDetailsService {

    User loadById(Long id);
    boolean existByUsername(String username);
    boolean existByEmail(String email);
    Long saveUser(User user);
}
