package com.example.workflow.delegates;

import com.example.workflow.dto.Pcsid;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import spinjar.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Base64;
import java.util.logging.Logger;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

@Component
public class GeneratePCSIDDelegate implements JavaDelegate {
    private final Logger logger = Logger.getLogger(GeneratePCSIDDelegate.class.getName());
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
    public static byte[] compress(String text) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            OutputStream out = new DeflaterOutputStream(baos);
            out.write(text.getBytes("UTF-8"));
            out.close();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return baos.toByteArray();
    }
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Pcsid pcsid=new Pcsid();
        pcsid.requestID= Long.valueOf(2518);
        pcsid.tokenType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
        pcsid.dispositionMessage="ISSUED";
       String tok="TUlJRVR6Q0NBL2FnQXdJQkFnSVRFUUFBQ2Ria2dQWpZV3d4RXpBUkJnb0praWFKay9Jc1pBRVpGZ05uYjNZeEZ6Qm5ZWHTVXVDBsRFJWTkRRVFF0UTBFd0hoY05Nak13TVRNeE1UTXdPREl3V2hjTk1qZ3dNVE13TVRNd09ESXdXakJrTVFzd0NRWURWUVFHRXdKVFFURVZNQk1HQTFVRUNoTU1RVUpEUkNCTWFXMXBkR1ZrTVJZd0ZBWURWUVFMRXcxU2FYbGhaR2dnUW5KaGJtTm9NU1l3SkFZRFZRUURFeDFVVTFRdE9EZzJORE14TVRRMUxUTXhNREE1TkRBeE1ETXdNREF3TXpCV01CQUdCeXFHU000OUFnRUdCU3VCQkFBS0EwSUFCRVdBMi91VnN5QmFDeEwwQnM1TDFKTXF1NlQxZFgwV3IvZ1RPaWpKbk1FSGJUbHdCTndNdzU2TEE3engvYnV1bWJMbTh5UThzZ01aaGNybU1lMWMwbXlqZ2dLS01JSUNoakNCc2dZRFZSMFJCSUdxTUlHbnBJR2tNSUdoTVRzd09RWURWUVFFRERJeExWUlRWSHd5TFZSVFZId3pMV1ZrTWpKbU1XUTRMV1UyWVRJdE1URXhPQzA1WWpVNExXUTVZVGhtTVRGbE5EUTFaakVmTUIwR0NnbVNKb21UOGl4a0FRRU1Eek14TURBNU5EQXhNRE13TURBd016RU5NQXNHQTFVRURBd0VNVEF3TURFUk1BOEdBMVVFR2d3SVVsSlNSREk1TWpreEh6QWRCZ05WQkE4TUZsSmxZV3dnWlhOMFlYUmxJR0ZqZEdsMmFYUnBaWE13SFFZRFZSME9CQllFRkE2M3RjMVVYbUlRekZhV0x1Q0dsNlFsK1pHeU1COEdBMVVkSXdRWU1CYUFGSnZLcXFMdG1xd3NrSUZ6VnZwUDJQeFQrOU5uTUU0R0ExVWRId1JITUVVd1E2QkJvRCtHUFdoMGRIQTZMeTlqY213MExucGhkR05oTG1kdmRpNXpZUzlEWlhKMFJXNXliMnhzTDFCU1drVkpUbFpQU1VORlUwTkJOQzFEUVNneEtTNWpjbXd3Z2FnR0NDc0dBUVVGQndFQkJJR2JNSUdZTUdzR0NDc0dBUVVGQnpBQ2hsOW9kSFJ3T2k4dllXbGhOQzU2WVhSallTNW5iM1l1YzJFdlEyVnlkRVZ1Y205c2JDOVFVbHBGU1c1MmIybGpaVk5EUVRRdVpYaDBaMkY2ZEM1bmIzWXViRzlqWVd4ZlVGSmFSVWxPVms5SlEwVlRRMEUwTFVOQktERXBMbU55ZERBcEJnZ3JCZ0VGQlFjd0FZWWRhSFIwY0RvdkwyTnliRFF1ZW1GMFkyRXVaMjkyTG5OaEwyOWpjM0F3RGdZRFZSMFBBUUgvQkFRREFnZUFNRHdHQ1NzR0FRUUJnamNWQndRdk1DMEdKU3NHQVFRQmdqY1ZDSUdHcUIyRTBQc1NodTJkSklmTyt4blR3RlZtaC9xbFpZWFpoRDRDQVdRQ0FSQXdIUVlEVlIwbEJCWXdGQVlJS3dZQkJRVUhBd01HQ0NzR0FRVUZCd01DTUNjR0NTc0dBUVFCZ2pjVkNnUWFNQmd3Q2dZSUt3WUJCUVVIQXdNd0NnWUlLd1lCQlFVSEF3SXdDZ1lJS29aSXpqMEVBd0lEUndBd1JBSWdNWDBHa1FCZHZFakhrWXEvU0pkV2ZRZjUzMWdzeVVLTXE4MGplV0NEK3FzQ0lFYWUxY3VyL29Ed01tT3RFS3B6L3k2RVZzbHdXWlhMbzZmTlowaUZKR1Y2";
        byte[] compressed=compress(tok);
        String invoiceString= Base64.getEncoder().encodeToString(compressed);
        pcsid.binarySecurityToken=invoiceString;
        pcsid.secret="V7e82a0Z09S87Qk5/1tVxKD8Oj93cRzxRtAVZ7XRYug=";
        ObjectMapper mapper2= new ObjectMapper();
        String result = mapper2.writeValueAsString(pcsid);

        delegateExecution.setVariable("pcsid",result);
    }
}
