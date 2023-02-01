package com.example.workflow.validation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class ValidationResultsImpl {

    private final List<ValidationResultImpl> infoMessages = new ArrayList<>();

    private final List<ValidationResultImpl> warningMessages = new ArrayList<>();

    private final List<ValidationResultImpl> errorMessages = new ArrayList<>();


    public void addInfoMessage(String code, String category, String message) {
        ValidationResultImpl validationMessage = new ValidationResultImpl(ValidationMessageType.INFO, code, category,
                message);

        infoMessages.add(validationMessage);
    }


    public void addWarningMessage(String code, String category, String message) {
        ValidationResultImpl validationMessage = new ValidationResultImpl(ValidationMessageType.WARNING, code, category,
                message);

        warningMessages.add(validationMessage);
    }


    public void addErrorMessage(String code, String category, String message) {
        ValidationResultImpl validationMessage = new ValidationResultImpl(ValidationMessageType.ERROR, code, category,
                message);

        errorMessages.add(validationMessage);
    }

    //TODO: validation all messages of type info

    public void addInfoMessages(List<ValidationResultImpl> infoMessages) {
        this.infoMessages.addAll(infoMessages);

    }

    //TODO: validation all messages of type warning

    public void addWarningMessages(List<ValidationResultImpl> warningMessages) {
        this.warningMessages.addAll(warningMessages);

    }

    //TODO: validation all messages of type error

    public void addErrorMessages(List<ValidationResultImpl> errorMessages) {
        this.errorMessages.addAll(errorMessages);
    }


    public ValidationStatus getStatus() {

        if (!errorMessages.isEmpty()) {
            return ValidationStatus.ERROR;
        } else if (!warningMessages.isEmpty()) {
            return ValidationStatus.WARNING;
        } else {
            return ValidationStatus.PASS;
        }
    }

}
