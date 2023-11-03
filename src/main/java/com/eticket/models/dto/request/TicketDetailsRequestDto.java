package com.eticket.models.dto.request;

import com.eticket.configurations.annotation.LicensePlateFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class TicketDetailsRequestDto {

    @NotBlank(message = "Serial number must not be blank")
    @Size(min = 6, message = "Serial number must have at least 6 characters")
    private String serialNumber;

    @PositiveOrZero(message = "Ticket amount cannot have negative value")
    private double amount;
    private String ticketPlace;

    @LicensePlateFormat(message = "Plate format is incorrect!")
    private String plateId;

    @NotBlank(message = "Breaker Full name must not be blank")
    private String breakerFullName;

    private ZonedDateTime ticketDate;
    private String vehicleType;
    @NotEmpty(message = "Broken laws must not be empty")
    private List<String> brokenLaws;

    @NotBlank(message = "Official Code must not be blank")
    @Size(min = 6, message = "Official Code must have at least 6 characters")
    private String officialCode;
}
