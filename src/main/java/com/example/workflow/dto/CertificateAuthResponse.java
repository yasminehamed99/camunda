package com.example.workflow.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CertificateAuthResponse implements Serializable {
    private Status certificateStatus;
    private String vatNumber;
    private String invoiceType;
    private String serialNumberForSolution;
    private String commonName;
    private String organizationUnitName;
    private String location;
    private String industry;
    private String organizationName;
    private String country;
    private CertificateType certificateType;
    private AuthStatus authStatus;
    private String CSIDserialNumber;
}