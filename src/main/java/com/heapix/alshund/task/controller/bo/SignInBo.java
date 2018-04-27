package com.heapix.alshund.task.controller.bo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class SignInBo {

    @Value("Bearer ")
    private final String tokenType;

    private String tokenValue;
}
