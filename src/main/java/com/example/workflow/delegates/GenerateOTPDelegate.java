package com.example.workflow.delegates;


import com.example.workflow.model.OtpGeneration;
import com.example.workflow.onboardingexception.OtpExceedingException;
import com.example.workflow.onboardingservice.AdminConfigRollsProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Component
public class GenerateOTPDelegate implements JavaDelegate {
    private final Logger logger = Logger.getLogger(GenerateOTPDelegate.class.getName());
    @Autowired
    AdminConfigRollsProvider rollsProvider;
    HttpHeaders headers = new HttpHeaders();

    RestTemplate restTemplate=new RestTemplate();


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String vat =(String) delegateExecution.getVariable("vat_reg_number");
        String maxNumOfOtpMultiDevices = "100";
        Integer maxNoOtp = Integer.valueOf(maxNumOfOtpMultiDevices);
        String num=(String)delegateExecution.getVariable("otp-number");
        int no=Integer.parseInt(num);

        logger.info("Creating a number of {} :  new otps." + no  );
        if (no > maxNoOtp)
            throw new OtpExceedingException("Otp requests exceeding the configured maximum number otp, limit = [" + maxNoOtp + "].");
        List<String> otps = new ArrayList<>();
        int otpLength =Integer.parseInt("6") ;

        for (int i = 1; i <= no; i++) {

            String otp = makeRandomOtp(otpLength);

            Date otpCreationDate = new Date();

            OtpGeneration otpGeneration = new OtpGeneration();
            otpGeneration.setOtp(otp);
            otpGeneration.setOtpCreationDate(otpCreationDate);
            otpGeneration.setVatNumber(vat);
            otpGeneration.setStatus(true);

//            headers.setContentType(APPLICATION_JSON);
//            HttpEntity<OtpGeneration> entity = new HttpEntity<OtpGeneration>(otpGeneration,headers);
//            String url
//                    = "http://localhost:8085/onboarding/save";
//            ResponseEntity<String> response = restTemplate.exchange(
//                    url, HttpMethod.POST, entity, String.class);
//            logger.info(response.getBody());
            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String requestBody = objectWriter.writeValueAsString(otpGeneration);
            HttpConnector http = Connectors.getConnector(HttpConnector.ID);
            HttpRequest request = http.createRequest();
            request.post()
                    .url("http://localhost:8085/onboarding/save")
                    .payload(requestBody)
                    .header("Content-Type", "application/json");
             request.execute();
//            otpGenerationRepository.save(otpGeneration);
            otps.add(otp);
            logger.info("Otp created {} : at time {} :" + otp +  otpCreationDate);

        }
        delegateExecution.setVariable("otps",otps);


    }
    private String makeRandomOtp(int length) throws NoSuchAlgorithmException {
        StringBuilder builder = new StringBuilder("");
        Random random = SecureRandom.getInstanceStrong();

        for(int i = 1; i <= length; i ++) {
            int value = random.nextInt(9 + 0);
            builder.append(value);
        }

        return builder.toString();
    }
}
