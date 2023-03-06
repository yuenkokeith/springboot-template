package com.microservices.accountservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptResponse {

    private String memberId;
    private Integer mallId;
    private Integer shopId;
    private double amount;
    private String invoiceNumber;
    private Integer receiptDateTime;

}
