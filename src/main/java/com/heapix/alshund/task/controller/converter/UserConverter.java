package com.heapix.alshund.task.controller.converter;

import com.heapix.alshund.task.controller.dto.UserDto;
import com.heapix.alshund.task.repository.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

public interface UserConverter {

    User dtoToUser(UserDto userDto);
    UserDto userToDto(User user);
}
