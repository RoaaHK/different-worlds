package com.example.DifferentWorlds.Repository;
import com.example.DifferentWorlds.Entity.PurchaseHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistoryEntity, Integer> {
    List<PurchaseHistoryEntity> findByCustomerId(Integer customerId);
}
