package com.example.workflow.process;

import com.example.workflow.exceptions.ProcessException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.logging.Logger;
@Component
public class Base64DecoderDelegate implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(Base64DecoderDelegate.class.getName());
    String encodedValue=null;
    String decodeValue=null;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
             encodedValue  = (String) delegateExecution.getVariable("encoded-invoice");
             decodeValue = new String(Base64.getDecoder().decode(encodedValue));
//            LOGGER.info(decodeValue);
            delegateExecution.setVariable("decodedInvoice",decodeValue);
        }catch(Exception e) {
            LOGGER.info(e.getMessage());
            throw new ProcessException("Invalid-Invoice","Invoice-Base64-Decoder-Error",
                    "Invalid encoded base 64 format");
        }



    }
}
