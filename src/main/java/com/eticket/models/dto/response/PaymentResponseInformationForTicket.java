package com.eticket.models.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaymentResponseInformationForTicket {

    private String serialNumber;
    private double totalCalculatedPaymentWithCommission;
    private int commissionRate;
    private String description;
}
