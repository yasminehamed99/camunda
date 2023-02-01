package com.example.workflow.exceptions;

import com.example.workflow.exceptions.ProcessConfigException;

public interface ProcessManager<T> {

    Process<T> getProcess(int processId) throws ProcessConfigException;
    Process<T> buildProcess(int processId) throws ProcessConfigException;
}
