package com.eticket.repository;

import com.eticket.models.OfficialEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOfficialRepository extends CrudRepository<OfficialEntity, Long> {

    Optional<OfficialEntity> findByOfficialCode(String officialCode);
}
