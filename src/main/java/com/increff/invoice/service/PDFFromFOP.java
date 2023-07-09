package com.increff.invoice.service;

import org.apache.fop.apps.*;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class PDFFromFOP {
    private final String xmlFilePath = "./src/main/resources/xml/Invoice.xml";
    private final String xslFilePath = "./src/main/resources/xsl/Invoice.xsl";
    private final String generatedDirPath = "./generated";

    public void createPDF() {
        try {
            File xmlFile = new File(xmlFilePath);
            File xsltFile = new File(xslFilePath);
            File pdfDir = new File(generatedDirPath);
            pdfDir.mkdirs();
            File pdfFile = new File(pdfDir, "invoice.pdf");

            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            OutputStream out = new FileOutputStream(pdfFile);
            out = new BufferedOutputStream(out);

            try {
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsltFile));

                Source src = new StreamSource(xmlFile);
                Result res = new SAXResult(fop.getDefaultHandler());

                transformer.transform(src, res);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
