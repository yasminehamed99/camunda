package com.example.workflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ZatcaProductionCSIDAuthRequest {

    @JsonProperty("certificate")
    private String certificateEncodedBase64;

}