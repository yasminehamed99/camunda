package com.example.workflow.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class Pcsid {
    public Long requestID;
    public String tokenType;
    public String dispositionMessage;
    public String binarySecurityToken ;
    public String secret;
}
