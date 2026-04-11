package com.example.DifferentWorlds.Repository;
import com.example.DifferentWorlds.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Integer>{
     Optional<CustomerEntity> findByUserName(String UserName);
     Optional<CustomerEntity> findByEmail(String email);
}
