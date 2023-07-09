package com.increff.invoice.controller;
import com.increff.invoice.model.InvoiceData;
import com.increff.invoice.service.InvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api
@RestController
public class GenerateInvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @ApiOperation(value = "Generate Invoice")
    @RequestMapping(path = "/api/invoice", method = RequestMethod.POST)
    public ResponseEntity<byte[]> getInvoice(@RequestBody InvoiceData form) throws IOException {
       return invoiceService.getInvoice(form);
    }

}