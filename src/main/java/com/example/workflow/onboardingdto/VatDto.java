package com.example.workflow.onboardingdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VatDto {

    @DecimalMin(value = "99999999999999", inclusive = false, message = "VAT is not a 15-digit number.")
    @Digits(integer = 15, fraction = 0, message = "VAT is not a 15-digit number.")
    @NotNull(message = "VAT is null.")
    private String vatRegistrationNumber;

    private String tinNumber;


}