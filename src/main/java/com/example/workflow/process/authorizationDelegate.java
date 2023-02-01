package com.example.workflow.process;
import camundajar.impl.com.google.gson.Gson;
import com.example.workflow.dto.AuthStatus;
import com.example.workflow.dto.CertificateAuthResponse;
import com.example.workflow.dto.CertificateType;
import com.example.workflow.dto.ZatcaCSIDAuthRequest;
import com.fasterxml.jackson.databind.*;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.logging.Logger;
@Component
public class authorizationDelegate implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(authorizationDelegate.class.getName());
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("validationResult","valid");
        String authentication= (String) delegateExecution.getVariable("authentication");
        String result;
        if (StringUtils.isBlank(authentication) || !authentication.startsWith("Basic")) {
            result= null;
        }
        String auth = authentication.replace("Basic", "").trim();
        try {
            String[] authParts = new String(Base64.getDecoder().decode(auth)).split(":");

            String username = authParts[0];
            String password = authParts[1];
            ZatcaCSIDAuthRequest zatcaCSIDAuthRequest = new ZatcaCSIDAuthRequest(username, password);
            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String requestBody = objectWriter.writeValueAsString(zatcaCSIDAuthRequest);
            HttpConnector http = Connectors.getConnector(HttpConnector.ID);
            HttpRequest request = http.createRequest();
            request.post()
                    .url("http://localhost:8012/core/auth/")
                    .payload(requestBody)
                    .header("Accept", "application/json");
            HttpResponse httpResponse = request.execute();

            LOGGER.info(  httpResponse.getResponse());
            Gson json=new Gson();
            CertificateAuthResponse certificateAuthResponse=json.fromJson(httpResponse.getResponse(),CertificateAuthResponse.class);
            if(AuthStatus.AUTHORIZED.equals(certificateAuthResponse.getAuthStatus()) && CertificateType.PRODUCTION.equals(certificateAuthResponse.getCertificateType())) {
              delegateExecution.setVariable("certificateAuthResponseMapEntry",new HashMap.SimpleEntry<>(username, certificateAuthResponse));
            }



        }
        catch (Exception ex) {
            result= ex.getMessage();
        }
        delegateExecution.setVariable("certificateAuthResponseMapEntry",new HashMap.SimpleEntry<>("valid", "certificateAuthResponse"));
    }



}
