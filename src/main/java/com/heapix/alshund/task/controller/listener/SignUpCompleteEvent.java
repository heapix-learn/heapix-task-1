package com.heapix.alshund.task.controller.listener;

import com.heapix.alshund.task.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class SignUpCompleteEvent extends ApplicationEvent {

    @NotNull
    private User user;

    @NotBlank
    private String applicationUrl;

    public SignUpCompleteEvent(User user, String applicationUrl) {

        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
