package com.heapix.alshund.task.service;

import com.heapix.alshund.task.controller.dto.UserDto;
import com.heapix.alshund.task.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface Converter {

    Converter instance = Mappers.getMapper(Converter.class);

    User dtoToUser(UserDto userDto);
    UserDto userToDto(User user);
}
