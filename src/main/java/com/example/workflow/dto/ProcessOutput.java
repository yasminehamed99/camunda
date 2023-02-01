package com.example.workflow.dto;

import java.util.Map;

public interface ProcessOutput<T> {
    void setArtifacts(Map<T, Object> artifacts);

    Map<T, Object> getArtifacts();
}
