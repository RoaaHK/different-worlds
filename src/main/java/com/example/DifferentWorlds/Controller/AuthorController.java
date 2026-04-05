package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.AuthorEntity;
import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import com.example.DifferentWorlds.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    // TODO avoid using auto wire as best practice of spring use dependency injection instead using constructor or setter
    private AuthorService authorService;

    @PostMapping("/addAuthor")
    public ResponseEntity<AuthorEntity> addAuthor(@RequestBody @Valid AuthorEntity author) {
        authorService.addAuthor(author);
        return new ResponseEntity <>(author, HttpStatus.CREATED);
    }

    @GetMapping("getAuthor/{id}")
    public ResponseEntity<AuthorEntity> getAuthor(@PathVariable Long id) {
        AuthorEntity author= authorService.getAuthor(id);
        return ResponseEntity.ok(author);
    }

//    @PutMapping("/updateAuthor/{id}")
//    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody@Valid Author author) {
//        author.setId(id);
//        authorService.addAuthor(author);
//        return ResponseEntity.ok(author);
//    }

    @DeleteMapping("/deleteAuthor/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/submitWork")
    public ResponseEntity<LiteraryWorksEntity> submitWork(@RequestBody @Valid LiteraryWorksEntity work) {
        LiteraryWorksEntity submittedWork= authorService.submitWork(work);
        return new ResponseEntity <>(submittedWork, HttpStatus.CREATED);
    }

    @GetMapping("/getWork/{id}")
    public ResponseEntity<LiteraryWorksEntity> getWork(@PathVariable Long id) {
        LiteraryWorksEntity work= authorService.getWork(id);
        return ResponseEntity.ok(work);
    }
}
