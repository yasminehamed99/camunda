package com.example.workflow.exceptions;

public class ProcessConfigException extends Exception {

    private static final long serialVersionUID = 1L;

    public ProcessConfigException(String message) {
        super(message);
    }

    public ProcessConfigException(String message, Throwable throwable) {
        super(message, throwable);
    }
}