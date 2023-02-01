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
import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.logging.Logger;
@Component
public class XsdValidatorDelegate implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(XsdValidatorDelegate.class.getName());
    public String invoice=null;
    public String   xsdFile=null;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ValidationResultsImpl results = new ValidationResultsImpl();
        ByteArrayInputStream byteArray = null;

        try {
            invoice = (String) delegateExecution.getVariable("decoded-invoice");
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
