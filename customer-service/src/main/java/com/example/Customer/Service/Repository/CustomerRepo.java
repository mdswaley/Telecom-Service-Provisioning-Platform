package com.example.Customer.Service.Repository;

import com.example.Customer.Service.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByEmail(String email);
    Optional<CustomerEntity> findByCustomerId(String customerId);
}
