package com.heapix.alshund.task.service;

import com.heapix.alshund.task.repository.model.User;
import com.heapix.alshund.task.repository.model.VerificationToken;
import com.heapix.alshund.task.repository.TokenRepository;
import com.heapix.alshund.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public User loadById(Long id) {

        return userRepository.findById(id).orElse(null);
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

        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }

    @Override
    public void saveVerificationToken(User user, String tokenValue) {

        VerificationToken verificationToken = VerificationToken.builder().user(user).token(tokenValue).build();
        tokenRepository.save(verificationToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username);
    }
}
