package com.example.workflow.validation;

import java.util.List;

public interface ValidationResults {
    List<ValidationResult>  getInfoMessages();

    List<ValidationResult> getWarningMessages();

    List<ValidationResult> getErrorMessages();

    void addInfoMessage(String code, String category, String message);

    void addWarningMessage(String code, String category, String message);

    void addErrorMessage(String code, String category, String message);

    void addInfoMessages(List<ValidationResult> infoMessages);

    void addWarningMessages(List<ValidationResult> warningMessages);

    void addErrorMessages(List<ValidationResult> errorMessages);

    ValidationStatus getStatus();

}
