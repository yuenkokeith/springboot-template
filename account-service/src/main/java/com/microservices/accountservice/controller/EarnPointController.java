package com.microservices.accountservice.controller;

import com.microservices.accountservice.response.ReceiptResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://api-gateway:9191", "http://localhost:9191"}, maxAge = 3600)
@RequestMapping("api/v1/earn-point")
public class EarnPointController {

    Logger LOGGER = LoggerFactory.getLogger(EarnPointController.class);

    @PostMapping("receipt")
    public ResponseEntity<ReceiptResponse> getReceipt() {

        ReceiptResponse receiptResponse = new ReceiptResponse();
        return new ResponseEntity<>(receiptResponse, HttpStatus.OK);
    }

}
