package com.example.workflow.validation;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import javax.xml.transform.stream.StreamSource;
import java.nio.file.Path;

public class ENXsltTransformerSingleton {


    private ENXsltTransformerSingleton() {

    }

    private static XsltTransformer transformer;
    private  static Path schematronFile;

    public static Path getSchematronFile() {
        return schematronFile;
    }

    public static void setSchematronFile(Path schematronFile) {
        ENXsltTransformerSingleton.schematronFile = schematronFile;
    }

    public static void xsltLoad() {

        if (transformer == null) {
            synchronized (XsltTransformer.class){
                try {
                    Processor processor = new Processor(false);
                    XsltCompiler compiler = processor.newXsltCompiler();
                    XsltExecutable xslt = compiler.compile(new StreamSource(schematronFile.toFile()));
                    transformer = xslt.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static XsltTransformer getTransformer(){
        if (transformer == null) {
            synchronized (XsltTransformer.class) {
                xsltLoad();
            }

        } return transformer;

    }

}
