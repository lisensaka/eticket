package com.eticket.converter;

import com.eticket.converter.mapper.CustomAutoMapper;
import com.eticket.models.OfficialEntity;
import com.eticket.models.dto.request.OfficialRequestDto;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class ToOfficialEntityConverter {
    private static final CustomAutoMapper customMapper = Mappers.getMapper(CustomAutoMapper.class);

    public OfficialEntity convertOfficialRequestDtoToOfficialEntity(OfficialRequestDto officialRequestDto){
        return customMapper.convertOfficialRequestDtoToOfficialEntity(officialRequestDto);
    }
}
