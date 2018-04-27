package com.heapix.alshund.task.controller.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class SignUpBo {

    @NotBlank
    private String message;

    @NotNull
    private boolean success;
}
