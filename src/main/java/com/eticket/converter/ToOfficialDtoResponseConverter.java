package com.eticket.converter;

import com.eticket.converter.mapper.CustomAutoMapper;
import com.eticket.models.OfficialEntity;
import com.eticket.models.dto.request.OfficialRequestDto;
import com.eticket.models.dto.response.OfficialResponseDto;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class ToOfficialDtoResponseConverter {
    private static final CustomAutoMapper customMapper = Mappers.getMapper(CustomAutoMapper.class);

    public OfficialResponseDto convertOfficialRequestDtoToOfficialEntity(OfficialEntity officialEntity){
        return customMapper.convertOfficialEntityToOfficialResponseDto(officialEntity);
    }
}
