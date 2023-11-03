package com.eticket.controller;

import com.eticket.models.dto.request.TicketDetailsRequestDto;
import com.eticket.models.dto.response.TicketDetailsResponseDto;
import com.eticket.service.TicketDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static com.eticket.configurations.constants.ConstantsConfiguration.TICKET_DETAILS_API_PATH;

@RestController
@RequestMapping(TICKET_DETAILS_API_PATH)
@RequiredArgsConstructor
public class TicketDetailsController {

    private final TicketDetailsService ticketDetailsService;

    @PostMapping
    public ResponseEntity<TicketDetailsResponseDto> createNewTicketDetailsEntity(
            @Valid @RequestBody TicketDetailsRequestDto ticketDetailsRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketDetailsService.createNewTicketDetailsEntity(ticketDetailsRequestDto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Set<TicketDetailsResponseDto>> getAllTicketDetailsResponseDto() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ticketDetailsService.getAllTicketDetailsResponseDto());
    }

    @GetMapping("/by-serial-number")
    public ResponseEntity<TicketDetailsResponseDto> getTicketDetailEntityBySerialNumber(
            @RequestParam("serialNumber") String serialNumber
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ticketDetailsService.getTicketDetailEntityBySerialNumber(serialNumber));
    }

    @PutMapping
    public ResponseEntity<TicketDetailsResponseDto> createNewTicketDetailsEntity(
            @RequestParam("serialNumber") String serialNumber,
            @Valid @RequestBody TicketDetailsRequestDto ticketDetailsRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ticketDetailsService.updateTicketDetailsEntityBySerialNumber(serialNumber, ticketDetailsRequestDto));
    }
}
