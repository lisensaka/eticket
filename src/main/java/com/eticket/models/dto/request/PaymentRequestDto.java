package com.eticket.models.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PaymentRequestDto {
    @NotBlank(message = "Serial number must not be blank!")
    @Size(min = 6, message = "Serial number must have at least 6 characters!")
    private String serialNumber;

//    @PositiveOrZero(message = "Ticket amount cannot have negative value!")
//    private double amount;

    @PositiveOrZero(message = "Total Payment cannot have negative value!")
    private double totalPayment;
}
