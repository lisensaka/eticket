package com.eticket.models.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class TicketDetailsResponseDto {
    private String serialNumber;
    private double amount;
    private String ticketPlace;
    private String plateId;
    private ZonedDateTime ticketDate;
    private String vehicleType;
    private List<String> brokenLaws;
    private String officialCode;
}
