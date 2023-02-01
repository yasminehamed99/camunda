package com.example.workflow.exceptions;

import com.example.workflow.dto.ProcessInput;
import com.example.workflow.dto.ProcessOutput;

public interface Process<T> {

    Integer getId();

    String getName();

    String getDescription();

    Double getVersion();

    ProcessOutput<T> process(ProcessInput<T> processInput) throws ProcessException;

}
