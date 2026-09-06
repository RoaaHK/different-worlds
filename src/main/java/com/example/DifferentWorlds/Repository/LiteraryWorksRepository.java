package com.example.DifferentWorlds.Repository;

import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiteraryWorksRepository extends JpaRepository<LiteraryWorksEntity,Integer>{
    Optional<LiteraryWorksEntity> findByTitle(String name);
//    Set<LiteraryWorksEntity> findByStatus(String status);
//    List<LiteraryWorksEntity> findByApprovalStatus(LiteraryWorkStatus literaryWorkStatus);
}
