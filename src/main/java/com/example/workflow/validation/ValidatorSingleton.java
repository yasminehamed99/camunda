package com.example.workflow.validation;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.nio.file.Path;

public class ValidatorSingleton {

    private ValidatorSingleton(){

    }

    private static  javax.xml.validation.Validator validator;

    private  static Path xsdFile;

    public static void setXsdFile(Path xsdFile) {
        ValidatorSingleton.xsdFile = xsdFile;
    }

    public static void setValidatorSingleton() throws SAXException {
        if (validator == null) {

            synchronized (javax.xml.validation.Validator.class){

                try {
                    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                    Schema schema = factory.newSchema(xsdFile.toFile());
                    validator = schema.newValidator();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static javax.xml.validation.Validator getValidator() throws SAXException {
        if (validator == null){
            synchronized (javax.xml.validation.Validator.class){
                setValidatorSingleton();
            }
        }
        return validator;
    }

}
