package com.eticket.repository;

import com.eticket.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByEmailEqualsIgnoreCase(String email);
}
