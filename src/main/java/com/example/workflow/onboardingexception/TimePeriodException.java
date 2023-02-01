package com.example.workflow.onboardingexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TimePeriodException extends RuntimeException {
    public TimePeriodException(String message) {
        super(message);
    }
}
