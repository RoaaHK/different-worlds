package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.Author;
import com.example.DifferentWorlds.Entity.LiteraryWorks;
import com.example.DifferentWorlds.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    // TODO avoid using auto wire as best practice of spring use dependency injection instead using constructor or setter
    private AuthorService authorService;

    @PostMapping("/addAuthor")
    public ResponseEntity<Author> addAuthor(@RequestBody @Valid Author author) {
        authorService.addAuthor(author);
        return new ResponseEntity <>(author, HttpStatus.CREATED);
    }

    @GetMapping("getAuthor/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
        Author author= authorService.getAuthor(id);
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
    public ResponseEntity<LiteraryWorks> submitWork(@RequestBody @Valid LiteraryWorks work) {
        LiteraryWorks submittedWork= authorService.submitWork(work);
        return new ResponseEntity <>(submittedWork, HttpStatus.CREATED);
    }

    @GetMapping("/getWork/{id}")
    public ResponseEntity<LiteraryWorks> getWork(@PathVariable Long id) {
        LiteraryWorks work= authorService.getWork(id);
        return ResponseEntity.ok(work);
    }
}
