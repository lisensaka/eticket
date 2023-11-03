package com.eticket.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.eticket.configurations.constants.ConstantsConfiguration.PAYMENT_ENTITY_TABLE_NAME;

@Entity
@Table(name = PAYMENT_ENTITY_TABLE_NAME)
@Getter
@Setter
public class PaymentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private double totalPayment;
    private double totalCalculatedPaymentWithCommission;
    private int commissionRate;
    private LocalDateTime paymentDate;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn
    private TicketDetailsEntity ticketDetailsEntity;

}
