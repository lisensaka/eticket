package com.eticket.service;

import com.eticket.converter.ToTicketDetailsConverter;
import com.eticket.converter.ToTicketDetailsDtoResponseConverter;
import com.eticket.exceptions.InvalidInputException;
import com.eticket.models.TicketDetailsEntity;
import com.eticket.models.dto.request.TicketDetailsRequestDto;
import com.eticket.models.dto.response.TicketDetailsResponseDto;
import com.eticket.repository.ITicketDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketDetailsService {

    private final ITicketDetailsRepository iTicketDetailsRepository;
    private final ToTicketDetailsConverter toTicketDetailsConverter;
    private final ToTicketDetailsDtoResponseConverter toTicketDetailsDtoResponseConverter;

    public TicketDetailsResponseDto createNewTicketDetailsEntity(TicketDetailsRequestDto ticketDetailsRequestDto) {
        try {
            TicketDetailsEntity ticketDetailsEntityConverted = toTicketDetailsConverter.convertTicketDetailsRequestDtoToTicketDetailsEntity(ticketDetailsRequestDto);
            TicketDetailsEntity savedTicketDetails = iTicketDetailsRepository.save(ticketDetailsEntityConverted);
            return toTicketDetailsDtoResponseConverter.convertTicketDetailsEntityToTicketDetailsDtoResponse(savedTicketDetails);
        } catch (ConstraintViolationException e) {
            throw new RuntimeException(String.format("Ticket with serial number: '%s', already exist in db!", ticketDetailsRequestDto.getSerialNumber()));
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<TicketDetailsResponseDto> getAllTicketDetailsResponseDto() {
        List<TicketDetailsEntity> ticketDetailsEntityList = (List<TicketDetailsEntity>) iTicketDetailsRepository.findAll();
        return ticketDetailsEntityList.stream()
                .map(toTicketDetailsDtoResponseConverter::convertTicketDetailsEntityToTicketDetailsDtoResponse)
                .collect(Collectors.toSet());
    }

    public TicketDetailsResponseDto getTicketDetailEntityBySerialNumber(String serialNumber) {
        TicketDetailsEntity ticketBySerialNumberEqualsIgnoreCase = getTicketBySerialNumberEqualsIgnoreCase(serialNumber);
        return toTicketDetailsDtoResponseConverter.convertTicketDetailsEntityToTicketDetailsDtoResponse(ticketBySerialNumberEqualsIgnoreCase);
    }

    public TicketDetailsResponseDto updateTicketDetailsEntityBySerialNumber(String serialNumber, TicketDetailsRequestDto ticketDetailsRequestDto) {
        TicketDetailsEntity ticketBySerialNumber = getTicketBySerialNumberEqualsIgnoreCase(serialNumber);
        toTicketDetailsConverter.updateTicketDetailsEntityFromTicketDetailsRequestDto(ticketDetailsRequestDto, ticketBySerialNumber);
        TicketDetailsEntity savedTicketDetailsEntity = iTicketDetailsRepository.save(ticketBySerialNumber);
        return toTicketDetailsDtoResponseConverter.convertTicketDetailsEntityToTicketDetailsDtoResponse(savedTicketDetailsEntity);
    }

    public TicketDetailsEntity getTicketBySerialNumberEqualsIgnoreCase(String serialNumber) {
        return iTicketDetailsRepository.findBySerialNumberEqualsIgnoreCase(serialNumber.trim())
                .orElseThrow(() -> new NoSuchElementException(String.format("Ticket Details with serial number :'%s', was not found!", serialNumber)));
    }
}
