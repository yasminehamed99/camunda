package com.example.workflow.process;

import camundajar.impl.com.google.gson.Gson;
import com.example.workflow.validation.ValidationResults;
import com.example.workflow.validation.ValidationResultsImpl;
import com.example.workflow.validation.ValidatorSingleton;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Base64;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.zip.InflaterInputStream;

@Component
public class XsdValidatorDelegate implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(XsdValidatorDelegate.class.getName());
    public String invoice=null;
    public String   xsdFile=null;
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
        ValidationResultsImpl results = new ValidationResultsImpl();
        ByteArrayInputStream byteArray = null;

        try {
            String invoice="";
            value="";
            for(int i=0;i<10;i++) {
                value += delegateExecution.getVariable("input" +( i + 1));
            }

            byte[] toBeDecompressed = Base64.getDecoder().decode(value);
            String invoiceEncoded = decompress(toBeDecompressed);
            LOGGER.info(invoice);
            invoice = new String(Base64.getDecoder().decode(invoiceEncoded));
            xsdFile = (String) delegateExecution.getVariable("xsd-file");
            ValidatorSingleton.setXsdFile(Path.of(xsdFile));
            LOGGER.info(Path.of(xsdFile).toString());
            ValidatorSingleton.setValidatorSingleton();
            javax.xml.validation.Validator validator = ValidatorSingleton.getValidator();

            synchronized (validator){

                byteArray=new ByteArrayInputStream(invoice.getBytes());
                validator.validate(new StreamSource(byteArray));

                results.addInfoMessage("XSD_VALID", "XSD validation",
                        "Complied with UBL 2.1 standards");
                byteArray.close();
            }
        }
        catch (Exception e) {
            LOGGER.info("Schema validation Exception : {} :" + e.getMessage());
            e.printStackTrace(); // For Testing
            results.addErrorMessage("XSD_INVALID", "XSD validation",
                    "Schema validation failed; XML does not comply with UBL 2.1 standards");
        }

      Gson g=new Gson();
        String json = g.toJson(results);
//        String json = objectWriter.writeValueAsString(results);

        delegateExecution.setVariable("validationResult", json);

        LOGGER.info(results.getInfoMessages().toString());
    }
}
