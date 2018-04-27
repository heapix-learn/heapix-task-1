package com.heapix.alshund.task.controller.converter;

import com.heapix.alshund.task.controller.dto.UserDto;
import com.heapix.alshund.task.repository.model.Role;
import com.heapix.alshund.task.repository.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverterIml implements UserConverter {

    @Override
    public User dtoToUser(UserDto userDto) {

        return User.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .authority(Role.USER)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(false)
                .build();
    }

    @Override
    public UserDto userToDto(User user) {

        return null;
    }
}
