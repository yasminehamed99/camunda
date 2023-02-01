package com.example.workflow.exceptions;


import com.example.workflow.dto.ErrorResponse;

public class CertificateAuthenticateException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 4360143687623474447L;

    private final ErrorResponse errorResponse;

    public CertificateAuthenticateException(ErrorResponse errorResponse) {

        this.errorResponse = errorResponse;
    }
}
