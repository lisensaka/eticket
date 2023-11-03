package com.eticket.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import static com.eticket.configurations.constants.ConstantsConfiguration.TICKET_DETAILS_ENTITY_TABLE_NAME;

@Entity
@Builder
@Table(name = TICKET_DETAILS_ENTITY_TABLE_NAME)
@Getter
@Setter
@AllArgsConstructor
public class TicketDetailsEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Column(unique = true)
    private String serialNumber;

    private double amount;
    private Double commissionAmount;
    private String ticketPlace;
    private String plateId;
    private String breakerFullName;
    private ZonedDateTime ticketDate;
    private ZonedDateTime ticketRegistrationDate;
    private String vehicleType;
    private Boolean isTicketDetailPaid;


    @ElementCollection
    @CollectionTable(name = "broken_laws")
    private List<String> brokenLaws;

    @ManyToOne
    private OfficialEntity officialEntity;

    public TicketDetailsEntity() {

    }
}
