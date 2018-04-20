package com.heapix.alshund.task.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class SignUpResponse {

    @NotBlank
    private String message;

    @NotNull
    private boolean success;
}
