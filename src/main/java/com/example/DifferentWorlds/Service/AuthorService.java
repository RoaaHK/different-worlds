package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.Author;
import com.example.DifferentWorlds.Entity.Customer;
import com.example.DifferentWorlds.Entity.LiteraryWorks;
import com.example.DifferentWorlds.Enums.LiteraryWorkStatus;
import com.example.DifferentWorlds.Repository.AuthorRepository;
import com.example.DifferentWorlds.Repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class AuthorService implements UserDetailsService {

    private final ItemRepository literaryWorkRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, ItemRepository literaryWorkRepository) {
        this.authorRepository = authorRepository;
        this.literaryWorkRepository = literaryWorkRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author = authorRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(author.getUserName(), author.getPassword(),
                new ArrayList<>());
    }

    @Transactional
    public LiteraryWorks submitWork(LiteraryWorks work) {
        //work.setApprovalStatus(LiteraryWorkStatus.PENDING);
        return literaryWorkRepository.save(work);
    }

    @Transactional(readOnly = true)
    public LiteraryWorks getWork(Long workId) {
        return literaryWorkRepository.findById(workId)
                .orElseThrow(() -> new EntityNotFoundException("Literary work not found with ID: " + workId));
    }

    @Transactional
    public void addAuthor(Author author) {
        if (authorRepository.findByUserName(author.getUserName()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        authorRepository.save(author);
    }

    @Transactional(readOnly = true)public Author getAuthor(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with ID: " + authorId));
    }

    @Transactional
    public void updateAuthor(Author author) {
        if (!authorRepository.existsById(author.getId())) {
            throw new EntityNotFoundException("Author not found with ID: " + author.getId());
        }
        authorRepository.save(author);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new EntityNotFoundException("Author with ID " + id + " does not exist.");
        }
        authorRepository.deleteById(id);
    }

    @Transactional
    public void deleteWork(Long workId) {
        if (!literaryWorkRepository.existsById(workId)) {
            throw new EntityNotFoundException("Work not found with ID: " + workId);
        }
        literaryWorkRepository.deleteById(workId);
    }
}
