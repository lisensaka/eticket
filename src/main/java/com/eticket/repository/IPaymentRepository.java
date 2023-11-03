package com.eticket.repository;

import com.eticket.models.PaymentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRepository extends CrudRepository<PaymentEntity, Long> {
}
