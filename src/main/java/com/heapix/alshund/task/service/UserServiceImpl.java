package com.heapix.alshund.task.service;

import com.heapix.alshund.task.model.User;
import com.heapix.alshund.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadById(Long id) {
        return userRepository.findById(id).orElse(null);//TODO:throw Exception;
    }

    @Override
    public boolean existByUsername(String username) {

        return Objects.equals(userRepository.existByUsername(username), BigInteger.ONE);
    }

    @Override
    public boolean existByEmail(String email) {
        return Objects.equals(userRepository.existByEmail(email), BigInteger.ONE);
    }

    @Override
    public Long saveUser(User user) {

        return userRepository.save(user).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username);
    }
}
