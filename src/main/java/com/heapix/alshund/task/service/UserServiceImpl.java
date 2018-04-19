package com.heapix.alshund.task.service;

import com.heapix.alshund.task.model.User;
import com.heapix.alshund.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);//TODO:throw Exception;
    }
}
