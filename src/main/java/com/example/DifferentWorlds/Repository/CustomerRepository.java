package com.example.DifferentWorlds.Repository;
import com.example.DifferentWorlds.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{
     Optional<Customer> findByUserName(String UserName);
     Optional<Customer> findByEmail(String email);
}
