package com.example.DifferentWorlds.Repository;

import com.example.DifferentWorlds.Entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    Optional<PaymentEntity> findByCustomer_UserName(String userName);

}
