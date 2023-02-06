package com.example.workflow.process;

import com.example.workflow.exceptions.ProcessException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.logging.Logger;
import java.util.zip.InflaterInputStream;

@Component
public class Base64DecoderDelegate implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(Base64DecoderDelegate.class.getName());
    String encodedValue=null;
    String decodeValue=null;
    String value=null;
    public static String decompress(byte[] bytes) {
        InputStream in = new InflaterInputStream(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[8192];
            int len;
            while((len = in.read(buffer))>0)
                baos.write(buffer, 0, len);
            return new String(baos.toByteArray(), "UTF-8");
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {

            encodedValue="";
            value="";
            for(int i=0;i<10;i++) {
                value += delegateExecution.getVariable("input" +( i + 1));
            }
            LOGGER.info(value);

            byte[] toBeDecompressed = Base64.getDecoder().decode(value);
            LOGGER.info(String.valueOf(toBeDecompressed));
            encodedValue = decompress(toBeDecompressed);
            LOGGER.info(encodedValue);

             decodeValue = new String(Base64.getDecoder().decode(encodedValue));
             LOGGER.info(decodeValue);
//            LOGGER.info(decodeValue);
            delegateExecution.setVariable("decodedInvoice","decodeValue");
        }catch(Exception e) {
            LOGGER.info(e.getMessage());
            throw new ProcessException("Invalid-Invoice","Invoice-Base64-Decoder-Error",
                    "Invalid encoded base 64 format");
        }



    }
}
