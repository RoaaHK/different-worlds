package com.example.DifferentWorlds.Repository;

import com.example.DifferentWorlds.Entity.Guest;
import com.example.DifferentWorlds.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    Optional<Payment> findByCustomerName(String userName);

}
