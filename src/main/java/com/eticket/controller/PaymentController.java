package com.eticket.controller;

import com.eticket.models.dto.request.PaymentRequestDto;
import com.eticket.models.dto.response.PaymentResponseInformationForTicket;
import com.eticket.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.eticket.configurations.constants.ConstantsConfiguration.PAYMENT_API_PATH;

@RestController
@RequestMapping(PAYMENT_API_PATH)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<PaymentResponseInformationForTicket> getPaymentDetailsForTicketBySerialNumberToBePaid(
            @RequestParam("serialNumber") String serialNumber
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(paymentService.getPaymentDetailsForTicketBySerialNumber(serialNumber));
    }

    @PostMapping("/make-payment")
    public ResponseEntity<String> createPaymentForTicketDetails(
            @Valid @RequestBody PaymentRequestDto paymentRequestDto
    ) {
        paymentService.createPaymentForTicketDetails(paymentRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Payment Succeed!");
    }
}
