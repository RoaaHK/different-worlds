package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.AuthorEntity;
import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import com.example.DifferentWorlds.Repository.AuthorRepository;
import com.example.DifferentWorlds.Repository.LiteraryWorksRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorService  {

    private final LiteraryWorksRepository literaryWorkRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, LiteraryWorksRepository literaryWorkRepository) {
        this.authorRepository = authorRepository;
        this.literaryWorkRepository = literaryWorkRepository;
    }

    @Transactional
    public LiteraryWorksEntity submitWork(LiteraryWorksEntity work) {
        //work.setApprovalStatus(LiteraryWorkStatus.PENDING);
        return literaryWorkRepository.save(work);
    }

    @Transactional(readOnly = true)
    public LiteraryWorksEntity getWork(Integer workId) {
        return literaryWorkRepository.findById(workId)
                .orElseThrow(() -> new EntityNotFoundException("Literary work not found with ID: " + workId));
    }

    @Transactional
    public void addAuthor(AuthorEntity author) {
        if (authorRepository.findByUserName(author.getUserName()).isPresent()) {
            throw new IllegalArgumentException(
                    "Username already exists: " + author.getUserName());
        }
        authorRepository.save(author);
    }

    @Transactional(readOnly = true)
    public AuthorEntity getAuthor(Integer authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Author not found with ID: " + authorId));
    }

    @Transactional(readOnly = true)
    public AuthorEntity getAuthorByUserName(String userName) {
        return authorRepository.findByUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Author not found with username: " + userName));
    }

//    @Transactional(readOnly = true)
//    public List<LiteraryWorksEntity> getWorksByAuthor(Integer authorId) {
//        AuthorEntity author = authorRepository.findById(authorId)
//                .orElseThrow(() -> new EntityNotFoundException(
//                        "Author not found with ID: " + authorId));
//        return List.copyOf(author.getWorks());
//    }

    @Transactional
    public void updatePassword(Integer authorId, String newEncodedPassword) {
        AuthorEntity existing = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Author not found with ID: " + authorId));
        existing.setPassword(newEncodedPassword);
        authorRepository.save(existing);
    }

    @Transactional
    public void deleteAuthor(Integer authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new EntityNotFoundException(
                    "Author not found with ID: " + authorId);
        }
        authorRepository.deleteById(authorId);
    }

//    @Transactional
//    public void deleteWork(Long workId) {
//        if (!literaryWorkRepository.existsById(workId)) {
//            throw new EntityNotFoundException("Work not found with ID: " + workId);
//        }
//        literaryWorkRepository.deleteById(workId);
//    }
}
