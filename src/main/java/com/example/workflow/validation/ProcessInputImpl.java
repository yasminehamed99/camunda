package com.example.workflow.validation;

import com.example.workflow.dto.ProcessInput;

import lombok.Data;

import java.util.Map;

@Data
public class ProcessInputImpl implements ProcessInput<String> {

    private Map<String, Object> data;

}
