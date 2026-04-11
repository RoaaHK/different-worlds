package com.example.DifferentWorlds.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiteraryWorksRepository extends JpaRepository<LiteraryWorksEntity,Integer>{
    Optional<LiteraryWorksEntity> findByTitle(String name);
//    Set<LiteraryWorks> findByStatus(String status);

}
