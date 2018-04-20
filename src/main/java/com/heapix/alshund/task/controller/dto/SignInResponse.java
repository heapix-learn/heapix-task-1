package com.heapix.alshund.task.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class SignInResponse {

    @Value("Bearer ")
    private final String tokenType;

    private String tokenValue;
}
