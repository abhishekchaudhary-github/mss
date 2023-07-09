package com.increff.invoice.service;

import com.increff.invoice.model.InvoiceData;
import com.increff.invoice.model.OrderItem;

import java.io.File;
import java.text.DecimalFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXMLFileJava {
    private final String xmlFilePath = "./src/main/resources/xml/Invoice.xml";
    private final DocumentBuilderFactory documentBuilderFactory;
    private final DecimalFormat decimalFormat;

    public CreateXMLFileJava() {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        decimalFormat = new DecimalFormat("#.##");
    }

    public void createXML(InvoiceData invoiceData) {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("invoice");
            document.appendChild(rootElement);

            appendElement(rootElement, "order_id", invoiceData.getOrderId().toString());
            appendElement(rootElement, "order_date", getOrderDate(invoiceData.getPlacedDate()));

            for (OrderItem orderItem : invoiceData.getOrderItemDataList()) {
                Element orderItemElement = document.createElement("order_item");
                rootElement.appendChild(orderItemElement);

                appendElement(orderItemElement, "id", orderItem.getOrderItemId().toString());
                appendElement(orderItemElement, "product_name", orderItem.getProductName());
                appendElement(orderItemElement, "quantity", orderItem.getQuantity().toString());
                appendElement(orderItemElement, "selling_price", decimalFormat.format(orderItem.getSellingPrice()));
                appendElement(orderItemElement, "amt", decimalFormat.format(orderItem.getAmt()));
            }

            appendElement(rootElement, "amount", invoiceData.getAmount().toString());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML file.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void appendElement(Element parentElement, String tagName, String textContent) {
        Document document = parentElement.getOwnerDocument();
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parentElement.appendChild(element);
    }

    private String getOrderDate(String placedDate) {
        return placedDate.replace("T", " ");
    }
}
