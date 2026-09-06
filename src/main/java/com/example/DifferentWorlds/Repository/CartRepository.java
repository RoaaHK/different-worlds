package com.example.DifferentWorlds.Repository;

import com.example.DifferentWorlds.Entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    List<CartEntity> findByCustomer_Id(Integer customerId);
    Optional<CartEntity> findByCustomer_IdAndLiteraryWork_ItemId(Integer customerId, Integer itemId);
    void deleteByCustomer_Id(Integer customerId);
}