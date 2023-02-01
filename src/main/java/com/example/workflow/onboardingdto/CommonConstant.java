package com.example.workflow.onboardingdto;

public final class CommonConstant {

    private  CommonConstant(){

    }

    public static final String MAX_NUMBER_OTP = "max.no.otp";
    public static final String MAX_MIN_OTP = "valid.otp.in.min";

    public static final String NOT_VALID ="Invalid";
    public static final String VALID ="Valid";


    public static final String EXPIRATION_ERROR_CODE = "0002";
    public static final String EXISTENCE_ERROR_CODE = "0003";


    public static final String EXPIRATION_ERROR_CATEGORY = "Expiration";
    public static final String EXISTENCE_ERROR_CATEGORY = "Not Existed";

    public static final String VAT_EXISTENCE_ERROR_MESSAGE = "Vat Registeration Number is not existed.";
    public static final String OTP_EXISTENCE_ERROR_MESSAGE = "OTP is not existed.";
    public static final String OTP_EXPIRATION_ERROR_MESSAGE = "OTP is expired.";
}
