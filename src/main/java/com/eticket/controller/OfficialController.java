package com.eticket.controller;

import com.eticket.models.dto.request.OfficialRequestDto;
import com.eticket.models.dto.response.OfficialResponseDto;
import com.eticket.service.OfficialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static com.eticket.configurations.constants.ConstantsConfiguration.OFFICIAL_API_PATH;

@RestController
@RequestMapping(OFFICIAL_API_PATH)
@RequiredArgsConstructor
public class OfficialController {

    private final OfficialService officialService;

    @PostMapping
    public ResponseEntity<OfficialResponseDto> createNewOfficial(
            @Valid @RequestBody OfficialRequestDto officialRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(officialService.createNewOfficial(officialRequestDto));
    }


    @GetMapping("/getAll")
    public ResponseEntity<Set<OfficialResponseDto>> getAllOfficialDtoResponse() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(officialService.getAllOfficialDtoResponse());
    }

    @GetMapping("/by-official-code")
    public ResponseEntity<OfficialResponseDto> getOfficialByOfficialCode(
            @RequestParam("officialCode") String officialCode
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(officialService.getOfficialByOfficialCode(officialCode));
    }

    @DeleteMapping("/by-official-code")
    public ResponseEntity<String> deleteOfficialByOfficialCode(
            @RequestParam("officialCode") String officialCode
    ) {
        officialService.deleteOfficialByOfficialCode(officialCode);
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("Official with official-code: '%s', was deleted successfully", officialCode));
    }
}
