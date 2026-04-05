package com.example.DifferentWorlds.Repository;

import com.example.DifferentWorlds.Entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long>{
//    public Optional<Guest> findByUserName(String userName);


}
