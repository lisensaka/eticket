package com.eticket.models.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class OfficialRequestDto {
    @NotBlank(message = "Official Name must not be blank")
    private String officialName;

    @NotBlank(message = "Official Code must not be blank")
    @Size(min = 6, message = "Official Code must have at least 6 characters")
    private String officialCode;

    private LocalDateTime birthDate;
}
