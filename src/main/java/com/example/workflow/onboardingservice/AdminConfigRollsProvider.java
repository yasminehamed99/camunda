package com.example.workflow.onboardingservice;

import com.example.workflow.onboardingdto.ResponseLookupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminConfigRollsProvider {

    @Value(value = "${admin.url}")
    private String adminLookupsUrl;

    @Autowired
    private RestTemplate restTemplate;

    private static final String TIMEPERIOD = "timePeriod";
    private static final String VALUE = "value";
    private static final String MAXNUMOFOTPMULTIDEVICES = "maxNumOfOtpMultiDevices";
    private static final String MAXNUMOFOTPDISPLAYED = "maxNumOfOtpDisplayed";
    private static final String MAXNUMBERDOCPERPAGE = "maxNumberDocPerPage";
    private static final String OTPLENGTH = "otpLength";



    public Map<String, String> getValuesFromAdmin() {
        ResponseEntity<ResponseLookupDto> metaDataMap = getMetaDataMap();
        String timePeriod = metaDataMap.getBody().getChilds().get(0).getMetaDataMap().get(TIMEPERIOD);
        String value = metaDataMap.getBody().getChilds().get(0).getMetaDataMap().get(VALUE);
        String maxNumOfOtpMultiDevices = metaDataMap.getBody().getChilds().get(1).getMetaDataMap().get(MAXNUMOFOTPMULTIDEVICES);
        String maxNumOfOtpDisplayed = metaDataMap.getBody().getChilds().get(1).getMetaDataMap().get(MAXNUMOFOTPDISPLAYED);
        String maxNumberDocPerPage = metaDataMap.getBody().getChilds().get(2).getMetaDataMap().get(MAXNUMBERDOCPERPAGE);
        String otpLength = metaDataMap.getBody().getMetaDataMap().get(OTPLENGTH);
        return getValues(timePeriod, value, maxNumOfOtpMultiDevices, maxNumOfOtpDisplayed, maxNumberDocPerPage, otpLength);
    }

    private Map<String, String> getValues(String timePeriod, String value, String maxNumOfOtpMultiDevices, String maxNumOfOtpDisplayed, String maxNumberDocPerPage, String otpLength) {
        Map<String, String> values = new HashMap<>();
        values.put(TIMEPERIOD, timePeriod);
        values.put(VALUE, value);
        values.put(MAXNUMOFOTPMULTIDEVICES, maxNumOfOtpMultiDevices);
        values.put(MAXNUMOFOTPDISPLAYED, maxNumOfOtpDisplayed);
        values.put(MAXNUMBERDOCPERPAGE, maxNumberDocPerPage);
        values.put(OTPLENGTH, otpLength);
        return values;
    }

    private ResponseEntity<ResponseLookupDto> getMetaDataMap() {
        try {
            return restTemplate.getForEntity(String.format(adminLookupsUrl, "2", "Root-Admin-Config-onBoarding"), ResponseLookupDto.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
