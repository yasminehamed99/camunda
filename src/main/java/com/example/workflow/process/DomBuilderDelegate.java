package com.example.workflow.process;

import com.example.workflow.exceptions.ProcessException;

import java.io.*;
import java.util.Base64;
import java.util.logging.Logger;
import java.util.zip.InflaterInputStream;

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
        public void execute(DelegateExecution delegateExecution) throws ProcessException {
        delegateExecution.setVariable("authenticationResponse","haaaaa");
        try {

            invoice="";
            value="";
            for(int i=0;i<10;i++) {
                value += delegateExecution.getVariable("input" +( i + 1));
            }
            LOGGER.info(value);

            byte[] toBeDecompressed = Base64.getDecoder().decode(value);
            LOGGER.info(String.valueOf(toBeDecompressed));
           String invoiceEncoded = decompress(toBeDecompressed);
            LOGGER.info(invoice);
            invoice = new String(Base64.getDecoder().decode(invoiceEncoded));

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
