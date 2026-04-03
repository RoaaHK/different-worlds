package com.example.DifferentWorlds.Repository;

import com.example.DifferentWorlds.Entity.Administrator;
import com.example.DifferentWorlds.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
// TODO the entity id is Integer and here you set as long.
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
     Optional<Administrator> findByUserName(String UserName);

}
