package com.increff.invoice.service;

import com.increff.invoice.model.InvoiceData;
import com.increff.invoice.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    public void getInvoice(InvoiceData form)
    {
        List<OrderItem> items = form.getOrderItemDataList();
        Double amt = 0.0;
        for(OrderItem i : items) {
            Double cur = 0.0;
            cur = i.getSellingPrice() * i.getQuantity();
            amt+=cur;
            i.setAmt(cur);
        }
        form.setAmount(amt);
        CreateXMLFileJava createXMLFileJava = new CreateXMLFileJava();

        createXMLFileJava.createXML(form);

        PDFFromFOP pdfFromFOP = new PDFFromFOP();

        pdfFromFOP.createPDF();
    }


}
