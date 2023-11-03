package com.eticket.service;

import com.eticket.converter.ToOfficialDtoResponseConverter;
import com.eticket.converter.ToOfficialEntityConverter;
import com.eticket.exceptions.CustomException;
import com.eticket.models.OfficialEntity;
import com.eticket.models.dto.request.OfficialRequestDto;
import com.eticket.models.dto.response.OfficialResponseDto;
import com.eticket.repository.IOfficialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfficialService {

    private final IOfficialRepository iOfficialRepository;
    private final ToOfficialEntityConverter toOfficialEntityConverter;
    private final ToOfficialDtoResponseConverter toOfficialDtoResponseConverter;

    public OfficialResponseDto createNewOfficial(OfficialRequestDto officialRequestDto) {
        try {
            OfficialEntity officialEntity = toOfficialEntityConverter.convertOfficialRequestDtoToOfficialEntity(officialRequestDto);
            OfficialEntity savedNewOfficialEntity = iOfficialRepository.save(officialEntity);
            return toOfficialDtoResponseConverter.convertOfficialRequestDtoToOfficialEntity(savedNewOfficialEntity);
        } catch (Exception e) {
            throw new CustomException(
                    String.format("Official with official-code: '%s', already exist!", officialRequestDto.getOfficialCode()), HttpStatus.BAD_REQUEST);
        }

    }

    public Set<OfficialResponseDto> getAllOfficialDtoResponse() {
        List<OfficialEntity> officialEntityList = (List<OfficialEntity>) iOfficialRepository.findAll();
        return officialEntityList.stream()
                .map(toOfficialDtoResponseConverter::convertOfficialRequestDtoToOfficialEntity)
                .collect(Collectors.toSet());
    }

    public OfficialResponseDto getOfficialByOfficialCode(String officialCode) {
        OfficialEntity byOfficialCode = getOfficialEntityByOfficialCode(officialCode);
        return toOfficialDtoResponseConverter.convertOfficialRequestDtoToOfficialEntity(byOfficialCode);
    }

    public void deleteOfficialByOfficialCode(String officialCode) {
        OfficialEntity byOfficialCode = getOfficialEntityByOfficialCode(officialCode);
        iOfficialRepository.delete(byOfficialCode);
    }

    public OfficialEntity getOfficialEntityByOfficialCode(String officialCode) {
        return iOfficialRepository.findByOfficialCode(officialCode).orElseThrow(
                () -> new NoSuchElementException(String.format("Official with official-code: '%s' , was not found!", officialCode))
        );
    }
}
