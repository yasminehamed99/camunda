package com.example.workflow.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@ToString

public class ErrorResponse implements Serializable {

    private String code;
    private String message;
    
    public ErrorResponse(String code, String message) {
    	this.code = code;
    	this.message = message;
    }

}
