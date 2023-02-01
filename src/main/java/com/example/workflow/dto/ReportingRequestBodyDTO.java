package com.example.workflow.dto;

import lombok.Data;

@Data
public class ReportingRequestBodyDTO {

    private String invoice;
    private String invoiceHash;
    private String uuid;

}
