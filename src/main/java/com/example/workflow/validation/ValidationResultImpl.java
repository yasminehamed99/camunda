package com.example.workflow.validation;

import lombok.Data;
import lombok.NonNull;

@Data
public class ValidationResultImpl  {

    @NonNull
    private final ValidationMessageType type;

    private final String code;

    private final String category;

    private final String message;

    public ValidationStatus getStatus() {

        switch (type) {
            case INFO:
                return ValidationStatus.PASS;
            case WARNING:
                return ValidationStatus.WARNING;
            case ERROR:
                return ValidationStatus.ERROR;
            default:
                return null;
        }
    }

}
