package com.eticket.converter.mapper;

import com.eticket.models.OfficialEntity;
import com.eticket.models.TicketDetailsEntity;
import com.eticket.models.dto.request.OfficialRequestDto;
import com.eticket.models.dto.request.TicketDetailsRequestDto;
import com.eticket.models.dto.response.OfficialResponseDto;
import com.eticket.models.dto.response.TicketDetailsResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper
public interface CustomAutoMapper {
//    @Mappings({
//            @Mapping(source = "trainingsDocumentLink", target = "trainingDocumentLink"),
//            @Mapping(target = "customStreamName", ignore = true)
//    }
//    )
//    CustomMethodDtoResponse convertCustomMethodToCustomMethodDtoResponseMapper(CustomMethod customMethod);\

    //
    @Mappings({
//            @Mapping(source = "trainingsDocumentLink", target = "trainingDocumentLink"),
//            @Mapping(target = "customStreamName", ignore = true)
    }
    )
    TicketDetailsEntity convertTicketDetailsRequestDtoToTicketDetailsEntity(TicketDetailsRequestDto ticketDetailsRequestDto);

    TicketDetailsResponseDto convertTicketDetailsEntityToTicketDetailsResponseDto(TicketDetailsEntity ticketDetailsEntity);

    OfficialEntity convertOfficialRequestDtoToOfficialEntity(OfficialRequestDto officialRequestDto);

    OfficialResponseDto convertOfficialEntityToOfficialResponseDto(OfficialEntity officialEntity);
    @Mappings({
//            @Mapping(source = "trainingsDocumentLink", target = "trainingDocumentLink"),
            @Mapping(target = "serialNumber", ignore = true)
    })
    void updateTicketDetailsEntityFromTicketDetailsRequestDto(TicketDetailsRequestDto ticketDetailsRequestDto, @MappingTarget TicketDetailsEntity ticketDetailsEntity);

}
