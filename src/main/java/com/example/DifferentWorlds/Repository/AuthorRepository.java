package com.example.DifferentWorlds.Repository;

import com.example.DifferentWorlds.Entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity,Integer> {
     Optional<AuthorEntity> findByUserName(String UserName);
}
