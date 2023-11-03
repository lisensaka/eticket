package com.eticket.converter;

import com.eticket.converter.mapper.CustomAutoMapper;
import com.eticket.exceptions.InvalidInputException;
import com.eticket.models.TicketDetailsEntity;
import com.eticket.models.dto.request.TicketDetailsRequestDto;
import com.eticket.service.OfficialService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class ToTicketDetailsConverter {
    private static final CustomAutoMapper customMapper = Mappers.getMapper(CustomAutoMapper.class);
    private final OfficialService officialService;

    public TicketDetailsEntity convertTicketDetailsRequestDtoToTicketDetailsEntity(TicketDetailsRequestDto ticketDetailsRequestDto) throws InvalidInputException {
        TicketDetailsEntity ticketDetailsEntity = customMapper.convertTicketDetailsRequestDtoToTicketDetailsEntity(ticketDetailsRequestDto);
        ticketDetailsEntity.setTicketRegistrationDate(convertLocalDateTimeToZonedDateTime());
        ticketDetailsEntity.setOfficialEntity( officialService.getOfficialEntityByOfficialCode(ticketDetailsRequestDto.getOfficialCode()));
        checkIfTicketDateIsValid(ticketDetailsRequestDto.getTicketDate(), ticketDetailsEntity);
        ticketDetailsEntity.setCommissionAmount(0.0);
        ticketDetailsEntity.setIsTicketDetailPaid(false);
        return ticketDetailsEntity;
    }

    public void updateTicketDetailsEntityFromTicketDetailsRequestDto(TicketDetailsRequestDto ticketDetailsRequestDto, TicketDetailsEntity ticketDetailsEntity){
        customMapper.updateTicketDetailsEntityFromTicketDetailsRequestDto(ticketDetailsRequestDto, ticketDetailsEntity);
    }

    private void checkIfTicketDateIsValid(ZonedDateTime ticketDate, TicketDetailsEntity ticketDetailsEntity) throws InvalidInputException {
        if(ticketDate.isAfter(convertLocalDateTimeToZonedDateTime())){
            throw new InvalidInputException("Ticket date is not valid!");
        }
        ticketDetailsEntity.setTicketDate(ticketDate);
    }

    private ZonedDateTime convertLocalDateTimeToZonedDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        return localDateTime.atZone(ZoneId.systemDefault());
    }

}
