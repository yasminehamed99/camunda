package com.example.workflow.validation;

public interface ValidationResult {
    ValidationMessageType getType();

    String getCode();

    String getCategory();

    String getMessage();

    ValidationStatus getStatus();
}
