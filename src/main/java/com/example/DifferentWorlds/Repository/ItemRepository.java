package com.example.DifferentWorlds.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.DifferentWorlds.Entity.LiteraryWorks;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<LiteraryWorks,Long>{
    Optional<LiteraryWorks> findByTitle(String name);
//    Set<LiteraryWorks> findByStatus(String status);

}
