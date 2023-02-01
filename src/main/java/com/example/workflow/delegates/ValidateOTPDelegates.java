package com.example.workflow.delegates;

import camundajar.impl.com.google.gson.Gson;
import com.example.workflow.model.OtpGeneration;
import com.example.workflow.onboardingdto.CommonConstant;
import com.example.workflow.onboardingdto.Error;
import com.example.workflow.onboardingdto.ValidationResponse;
import com.example.workflow.onboardingexception.TimePeriodException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spinjar.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ValidateOTPDelegates implements JavaDelegate {
    private final Logger logger = Logger.getLogger(ValidateOTPDelegates.class.getName());
    @Value("${OTP_VALIDITY}")
    private double otpValidity;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ValidationResponse validationResponse=new ValidationResponse();

        String otp=(String) delegateExecution.getVariable("v_otp");
        String vat=(String) delegateExecution.getVariable("vatNumber");

        logger.info("Validating Otp {} :   and Vat {} :" +otp +vat);

        HttpConnector httpp = Connectors.getConnector(HttpConnector.ID);
        HttpRequest request2 = httpp.createRequest();
        request2.get()
                .url("http://localhost:8085/onboarding/getOtp?otp="+otp+"&vat="+vat)
                .header("Content-Type", "application/json");
        HttpResponse httpResponse = request2.execute();
        logger.info(httpResponse.getResponse());
        Gson g = new Gson();
        OtpGeneration otpGeneration = g.fromJson(httpResponse.getResponse(), OtpGeneration.class);

//        OtpGeneration otpGeneration = otpGenerationRepository.findByOtpAndVat(otp, vat);


        if (otpGeneration == null) {
            logger.info("invalid");
            validationResponse= createValidationResponse(CommonConstant.NOT_VALID, CommonConstant.EXISTENCE_ERROR_CODE,
                    CommonConstant.EXISTENCE_ERROR_CATEGORY, CommonConstant.VAT_EXISTENCE_ERROR_MESSAGE);
        }

        otpGeneration.setStatus(false);
        ObjectWriter objectWriter = new com.fasterxml.jackson.databind.ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = objectWriter.writeValueAsString(otpGeneration);
        HttpConnector http = Connectors.getConnector(HttpConnector.ID);
        HttpRequest request = http.createRequest();
        request.post()
                .url("http://localhost:8085/onboarding/save")
                .payload(requestBody)
                .header("Content-Type", "application/json");
        request.execute();
//        otpGenerationRepository.save(otpGeneration);

        HttpConnector http2 = Connectors.getConnector(HttpConnector.ID);
        HttpRequest request3 = http2.createRequest();
        request3.get()
                .url("http://localhost:8085/onboarding/getList?otp="+otp)
                .header("Content-Type", "application/json");
       HttpResponse httpResponse1= request3.execute();

        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        List<OtpGeneration> existedOtps = mapper.readValue(httpResponse1.getResponse(), new TypeReference<List<OtpGeneration>>(){});
//        List<OtpGeneration> existedOtps = otpGenerationRepository.findByOtpByOtpCreationDateDesc(otp);

        if (existedOtps.isEmpty()) {
            delegateExecution.setVariable("valid",false);
            logger.info("invalid");
            validationResponse= createValidationResponse(CommonConstant.NOT_VALID, CommonConstant.EXISTENCE_ERROR_CODE, CommonConstant.EXISTENCE_ERROR_CATEGORY, CommonConstant.OTP_EXISTENCE_ERROR_MESSAGE);
        }

        long creationDate = existedOtps.get(0).getOtpCreationDate().getTime();
        long currentDate = new Date().getTime();
        long different = currentDate - creationDate;
        double elapsedMinutes = different /otpValidity;


        String timePeriod = "Minutes";
        String value = "15";
        Integer valueO = Integer.valueOf(value);
        long  configuredMinutes=getConfiguredMinutes(timePeriod,valueO);

        if (elapsedMinutes > configuredMinutes) {
            delegateExecution.setVariable("valid",false);
            logger.info("invalid");

            validationResponse= createValidationResponse(CommonConstant.NOT_VALID, CommonConstant.EXPIRATION_ERROR_CODE, CommonConstant.EXPIRATION_ERROR_CATEGORY, CommonConstant.OTP_EXPIRATION_ERROR_MESSAGE);
        }
        else {

            validationResponse =createValidationResponse(CommonConstant.VALID, null, null, null);
        delegateExecution.setVariable("valid",true);}


        logger.info("valid");

        ObjectMapper mapper2= new ObjectMapper();
        String result = mapper2.writeValueAsString(validationResponse);
        delegateExecution.setVariable("validationResult",result);

    }
    private ValidationResponse createValidationResponse(String result, String errorCode, String errorCategory, String errorMessage) {

        ValidationResponse validationResponse = new ValidationResponse();

        validationResponse.setResult(result);


        Error[] errors=new Error[1];

        Error error = new Error();
        error.setErrorCode(errorCode);
        error.setErrorCategory(errorCategory);
        error.setErrorMessage(errorMessage);

        errors[0] = error;
        validationResponse.setError(errors);

        return validationResponse;
    }
    private long getConfiguredMinutes(String timePeriod, int value){
        if(timePeriod.equalsIgnoreCase("Days")){
            return (long) value * 24 * 60;
        }
        else if(timePeriod.equalsIgnoreCase("Hours")){
            return (long) value * 60 ;
        }
        else if(timePeriod.equalsIgnoreCase("Minutes")){
            return value;
        }
        else if(timePeriod.equalsIgnoreCase("seconds")){
            return (long) value/60 ;
        }
        else{
            throw new TimePeriodException("there is no such Period : " +timePeriod);
        }
    }


}
