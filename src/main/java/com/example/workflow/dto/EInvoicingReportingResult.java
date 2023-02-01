package com.example.workflow.dto;

import com.example.workflow.validation.ValidationResults;
import lombok.Data;

@Data
public class EInvoicingReportingResult {

    private ValidationResults validationResults;

    private ReportingStatus reportingStatus;
}
