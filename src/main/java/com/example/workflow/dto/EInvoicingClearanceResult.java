package com.example.workflow.dto;


import com.example.workflow.validation.ValidationResults;
import lombok.Data;

@Data
public class EInvoicingClearanceResult {

    private ValidationResults validationResults;

    private ClearanceStatus clearanceStatus;

    private String clearedInvoice;
}
