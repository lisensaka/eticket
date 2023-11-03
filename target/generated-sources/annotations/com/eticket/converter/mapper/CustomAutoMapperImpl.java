package com.eticket.converter.mapper;

import com.eticket.models.OfficialEntity;
import com.eticket.models.TicketDetailsEntity;
import com.eticket.models.dto.request.OfficialRequestDto;
import com.eticket.models.dto.request.TicketDetailsRequestDto;
import com.eticket.models.dto.response.OfficialResponseDto;
import com.eticket.models.dto.response.TicketDetailsResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-03T13:09:18+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8.1 (Private Build)"
)
public class CustomAutoMapperImpl implements CustomAutoMapper {

    @Override
    public TicketDetailsEntity convertTicketDetailsRequestDtoToTicketDetailsEntity(TicketDetailsRequestDto ticketDetailsRequestDto) {
        if ( ticketDetailsRequestDto == null ) {
            return null;
        }

        TicketDetailsEntity.TicketDetailsEntityBuilder ticketDetailsEntity = TicketDetailsEntity.builder();

        ticketDetailsEntity.serialNumber( ticketDetailsRequestDto.getSerialNumber() );
        ticketDetailsEntity.amount( ticketDetailsRequestDto.getAmount() );
        ticketDetailsEntity.ticketPlace( ticketDetailsRequestDto.getTicketPlace() );
        ticketDetailsEntity.plateId( ticketDetailsRequestDto.getPlateId() );
        ticketDetailsEntity.breakerFullName( ticketDetailsRequestDto.getBreakerFullName() );
        ticketDetailsEntity.ticketDate( ticketDetailsRequestDto.getTicketDate() );
        ticketDetailsEntity.vehicleType( ticketDetailsRequestDto.getVehicleType() );
        List<String> list = ticketDetailsRequestDto.getBrokenLaws();
        if ( list != null ) {
            ticketDetailsEntity.brokenLaws( new ArrayList<String>( list ) );
        }

        return ticketDetailsEntity.build();
    }

    @Override
    public TicketDetailsResponseDto convertTicketDetailsEntityToTicketDetailsResponseDto(TicketDetailsEntity ticketDetailsEntity) {
        if ( ticketDetailsEntity == null ) {
            return null;
        }

        TicketDetailsResponseDto ticketDetailsResponseDto = new TicketDetailsResponseDto();

        ticketDetailsResponseDto.setSerialNumber( ticketDetailsEntity.getSerialNumber() );
        ticketDetailsResponseDto.setAmount( ticketDetailsEntity.getAmount() );
        ticketDetailsResponseDto.setTicketPlace( ticketDetailsEntity.getTicketPlace() );
        ticketDetailsResponseDto.setPlateId( ticketDetailsEntity.getPlateId() );
        ticketDetailsResponseDto.setTicketDate( ticketDetailsEntity.getTicketDate() );
        ticketDetailsResponseDto.setVehicleType( ticketDetailsEntity.getVehicleType() );
        List<String> list = ticketDetailsEntity.getBrokenLaws();
        if ( list != null ) {
            ticketDetailsResponseDto.setBrokenLaws( new ArrayList<String>( list ) );
        }

        return ticketDetailsResponseDto;
    }

    @Override
    public OfficialEntity convertOfficialRequestDtoToOfficialEntity(OfficialRequestDto officialRequestDto) {
        if ( officialRequestDto == null ) {
            return null;
        }

        OfficialEntity officialEntity = new OfficialEntity();

        officialEntity.setOfficialName( officialRequestDto.getOfficialName() );
        officialEntity.setOfficialCode( officialRequestDto.getOfficialCode() );
        officialEntity.setBirthDate( officialRequestDto.getBirthDate() );

        return officialEntity;
    }

    @Override
    public OfficialResponseDto convertOfficialEntityToOfficialResponseDto(OfficialEntity officialEntity) {
        if ( officialEntity == null ) {
            return null;
        }

        OfficialResponseDto officialResponseDto = new OfficialResponseDto();

        officialResponseDto.setOfficialName( officialEntity.getOfficialName() );
        officialResponseDto.setOfficialCode( officialEntity.getOfficialCode() );

        return officialResponseDto;
    }

    @Override
    public void updateTicketDetailsEntityFromTicketDetailsRequestDto(TicketDetailsRequestDto ticketDetailsRequestDto, TicketDetailsEntity ticketDetailsEntity) {
        if ( ticketDetailsRequestDto == null ) {
            return;
        }

        ticketDetailsEntity.setAmount( ticketDetailsRequestDto.getAmount() );
        ticketDetailsEntity.setTicketPlace( ticketDetailsRequestDto.getTicketPlace() );
        ticketDetailsEntity.setPlateId( ticketDetailsRequestDto.getPlateId() );
        ticketDetailsEntity.setBreakerFullName( ticketDetailsRequestDto.getBreakerFullName() );
        ticketDetailsEntity.setTicketDate( ticketDetailsRequestDto.getTicketDate() );
        ticketDetailsEntity.setVehicleType( ticketDetailsRequestDto.getVehicleType() );
        if ( ticketDetailsEntity.getBrokenLaws() != null ) {
            List<String> list = ticketDetailsRequestDto.getBrokenLaws();
            if ( list != null ) {
                ticketDetailsEntity.getBrokenLaws().clear();
                ticketDetailsEntity.getBrokenLaws().addAll( list );
            }
            else {
                ticketDetailsEntity.setBrokenLaws( null );
            }
        }
        else {
            List<String> list = ticketDetailsRequestDto.getBrokenLaws();
            if ( list != null ) {
                ticketDetailsEntity.setBrokenLaws( new ArrayList<String>( list ) );
            }
        }
    }
}
