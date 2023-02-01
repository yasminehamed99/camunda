package com.example.workflow.process;

import com.example.workflow.exceptions.ProcessException;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



@Component
public class DomBuilderDelegate implements JavaDelegate {
    private final java.util.logging.Logger LOGGER = Logger.getLogger(DomBuilderDelegate.class.getName());
    public String invoice=null;

    @Override
        public void execute(DelegateExecution delegateExecution) throws ProcessException {
        delegateExecution.setVariable("authenticationResponse","haaaaa");
        try {

            invoice = (String) delegateExecution.getVariable("decoded-invoice");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            LOGGER.info(invoice);

            InputSource is = new InputSource(new StringReader(invoice));
            Document doc = db.parse(is);
            delegateExecution.setVariable("invoiceDom",doc);
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            delegateExecution.setVariable("xmlPath",xpath.toString());

        } catch (SAXException | IOException e) {
            e.printStackTrace();
            LOGGER.info("Exception : {} : " + e.getMessage());
            throw new ProcessException("XSD_INVALID", "XSD validation",
                    "Schema validation failed; XML does not comply with UBL 2.1 standards ");
        } catch (ParserConfigurationException e) {
            LOGGER.info("Exception : {} " +e);
            throw new ProcessException("XSD_INVALID", "XSD validation",
                    "Schema validation failed; XML does not comply with UBL 2.1 standards ");
        }

    }
}
