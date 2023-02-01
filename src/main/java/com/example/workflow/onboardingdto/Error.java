package com.example.workflow.onboardingdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Error {

    private String errorCode;
    private String errorCategory;
    private String errorMessage;
}
