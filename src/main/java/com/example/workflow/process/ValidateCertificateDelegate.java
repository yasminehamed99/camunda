package com.example.workflow.process;

import com.example.workflow.component.CertificateAuthResponse;
import com.example.workflow.config.ApplicationContextHolder;
import com.example.workflow.dto.AuthStatus;
import com.example.workflow.dto.CertificateType;
import com.example.workflow.dto.Status;
import com.example.workflow.exceptions.ProcessException;
import com.example.workflow.validation.ValidationResults;
import com.example.workflow.validation.ValidationResultsImpl;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.XdmDestination;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XsltTransformer;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Component
public class ValidateCertificateDelegate implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(ValidateCertificateDelegate.class.getName());
    private static final String AUTHENTICATION_ERRORS = "Authentication-Errors";
    private static final String INVALID_AUTHENTICATION_CERTIFICATE = "Invalid-Authentication-Certificate";


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String authentication= (String) delegateExecution.getVariable("authentication-certificate");
        if(authentication.equals("valid")){
            delegateExecution.setVariable("statusCode",true);
        }
        else{
            delegateExecution.setVariable("statusCode",false);

        }
//        delegateExecution.setVariable("validationStatus",results);

//        ValidationResults results = new ValidationResultsImpl();
//        try {
//            String authCertificate = (String) delegateExecution.getVariable("authentication-certificate");
//            if (StringUtils.isBlank(authCertificate)) {
//                results.addErrorMessage("Missing-Authentication-Certificate", AUTHENTICATION_ERRORS, "Production CSID is not present in the API header");
//            } else {
//                checkCertificate(delegateExecution);
//            }
//        } catch (Exception ex) {
//            LOGGER.info("Exception : {}"+ ex.getMessage());
//            throw new ProcessException(AUTHENTICATION_ERRORS, INVALID_AUTHENTICATION_CERTIFICATE, "Production CSID is not valid");
//        }
////        return results;
    }
//    private void checkCertificate(DelegateExecution delegateExecution) throws ProcessException {
//        try {
//            CertificateAuthResponse certificateAuthResponse=(CertificateAuthResponse) delegateExecution.getVariable("authentication-response");
//            if (Status.GOOD.equals(certificateAuthResponse.getCertificateStatus())) {
//                delegateExecution.setVariable("status-code",certificateAuthResponse.getCertificateStatus());
//
//            } else if (Status.EXPIRED.equals(certificateAuthResponse.getCertificateStatus())) {
//                throw new ProcessException(AUTHENTICATION_ERRORS, INVALID_AUTHENTICATION_CERTIFICATE, "Production CSID has expired");
//            } else if (Status.REVOKED.equals(certificateAuthResponse.getCertificateStatus())) {
//                throw new ProcessException(AUTHENTICATION_ERRORS, INVALID_AUTHENTICATION_CERTIFICATE, "Production CSID has been revoked");
//            } else {
//                throw new ProcessException(AUTHENTICATION_ERRORS, INVALID_AUTHENTICATION_CERTIFICATE, "Production CSID format is not valid");
//            }
//        } catch (Exception e) {
//            throw new ProcessException(AUTHENTICATION_ERRORS, INVALID_AUTHENTICATION_CERTIFICATE, "Production CSID format is not valid");
//        }
//    }
}
