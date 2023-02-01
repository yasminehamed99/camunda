package com.example.workflow.exceptions;

import lombok.Data;

@Data
public class ProcessException extends Exception {

    private static final long serialVersionUID = 1L;

    private String category;
    private String code;
    private String message;

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(String category, String code, String message) {
        this.category = category;
        this.code = code;
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
