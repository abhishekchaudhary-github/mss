package com.increff.invoice.service;

import com.increff.invoice.model.InvoiceData;
import com.increff.invoice.model.OrderItem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;



@Service
public class InvoiceService {
    public ResponseEntity<byte[]> getInvoice(InvoiceData form) throws IOException {
        List<OrderItem> items = form.getOrderItemDataList();
        Double amt = 0.0;
        for (OrderItem i : items) {
            Double cur = 0.0;
            cur = i.getSellingPrice() * i.getQuantity();
            amt += cur;
            i.setAmt(cur);
        }
        form.setAmount(amt);
        CreateXMLFileJava createXMLFileJava = new CreateXMLFileJava();

        createXMLFileJava.createXML(form);

        PDFFromFOP pdfFromFOP = new PDFFromFOP();

        pdfFromFOP.createPDF();


        String _filename = "./generated/invoice_" + form.getOrderId() + ".pdf";
        Path pdfPath = Paths.get("./generated/invoice.pdf");

        byte[] contents = Base64.getEncoder().encode(Files.readAllBytes(pdfPath));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;

    }
}