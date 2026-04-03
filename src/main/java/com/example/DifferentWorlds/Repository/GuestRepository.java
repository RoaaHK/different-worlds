package com.example.DifferentWorlds.Repository;

import com.example.DifferentWorlds.Entity.Customer;
import com.example.DifferentWorlds.Entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface GuestRepository extends JpaRepository<Guest, Long>{
//    public Optional<Guest> findByUserName(String userName);


}
