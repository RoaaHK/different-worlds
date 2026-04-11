package com.example.DifferentWorlds.Repository;

import com.example.DifferentWorlds.Entity.AdministratorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
// TODO the entity id is Integer and here you set as long.
public interface AdministratorRepository extends JpaRepository<AdministratorEntity, Integer> {
     Optional<AdministratorEntity> findByUserName(String userName);

}
