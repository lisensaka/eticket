package com.eticket.repository;

import com.eticket.models.TicketDetailsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITicketDetailsRepository extends CrudRepository<TicketDetailsEntity, Long> {

    Optional<TicketDetailsEntity> findBySerialNumberEqualsIgnoreCase(String serialNumber);
}
