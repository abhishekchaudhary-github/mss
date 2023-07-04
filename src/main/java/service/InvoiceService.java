package service;

import model.InvoiceData;
import model.OrderItemData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    public void generateInvoice(InvoiceData form)
    {
        List<OrderItemData> items = form.getOrderItemDataList();
        Double amt = 0.0;
        for(OrderItemData i : items) {
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
