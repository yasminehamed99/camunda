package com.example.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Variables {
    public Certificate certificate;
    public XsdFile xsdFile;
    public Invoice invoice;
    public Language language;
    public EnRules enRules;
    public Authentication authentication;
    public AuthenticationResponse authenticationResponse;
    public BusinessRules businessRules;
    public ValidationResult validationResult;

}
