package com.example.workflow.dto;

import java.util.Map;

public interface ProcessInput<T> {

    Map<T, Object> getData();

    void setData(Map<T, Object> data);
}
