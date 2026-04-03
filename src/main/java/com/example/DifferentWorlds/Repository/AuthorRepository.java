package com.example.DifferentWorlds.Repository;

import com.example.DifferentWorlds.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
     Optional<Author> findByUserName(String UserName);
}
