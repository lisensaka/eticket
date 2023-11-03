package com.eticket.converter;

import com.eticket.converter.mapper.CustomAutoMapper;
import com.eticket.models.TicketDetailsEntity;
import com.eticket.models.dto.response.TicketDetailsResponseDto;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class ToTicketDetailsDtoResponseConverter {
    private static final CustomAutoMapper customMapper = Mappers.getMapper(CustomAutoMapper.class);

    public TicketDetailsResponseDto convertTicketDetailsEntityToTicketDetailsDtoResponse(TicketDetailsEntity ticketDetailsEntity) {
        TicketDetailsResponseDto ticketDetailsResponseDto = customMapper.convertTicketDetailsEntityToTicketDetailsResponseDto(ticketDetailsEntity);
        ticketDetailsResponseDto.setOfficialCode(ticketDetailsEntity.getOfficialEntity().getOfficialCode());
        return ticketDetailsResponseDto;
    }

}
