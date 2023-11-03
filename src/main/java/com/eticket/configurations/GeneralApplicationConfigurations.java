package com.eticket.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Configuration
public class GeneralApplicationConfigurations {

    @Bean
    public OffsetDateTime convertLocalDateTimeToOffsetDateTime() {
        LocalDateTime l = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        ZonedDateTime z = l.atZone(ZoneId.systemDefault());
        return z.toOffsetDateTime();
    }

    @Bean
    public ZonedDateTime convertLocalDateTimeToZonedDateTime() {
        LocalDateTime l = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        ZonedDateTime z = l.atZone(ZoneId.systemDefault());
        return z;
    }
}
