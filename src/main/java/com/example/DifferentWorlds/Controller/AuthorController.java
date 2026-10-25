package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.AuthorEntity;
import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import com.example.DifferentWorlds.Service.AuthorService;
import com.example.DifferentWorlds.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("@accessChecker.canAccessAuthor(#id)")
    public ResponseEntity<UserResponseDTO> getAuthorById(@PathVariable Integer id) {
        AuthorEntity author = authorService.getAuthor(id);
        return ResponseEntity.ok(new UserResponseDTO(
                author.getId(), author.getName(), author.getUserName(), author.getEmail(), "AUTHOR"));
    }

    @GetMapping("/by-username/{userName}")
    @PreAuthorize("@accessChecker.canAccessAuthorByUserName(#userName)")
    public ResponseEntity<UserResponseDTO> getAuthorByUserName(@PathVariable String userName) {
        AuthorEntity author = authorService.getAuthorByUserName(userName);
        return ResponseEntity.ok(new UserResponseDTO(
                author.getId(), author.getName(), author.getUserName(), author.getEmail(), "AUTHOR"));
    }

    @PatchMapping("/{id}/password")
    @PreAuthorize("@accessChecker.canAccessAuthor(#id)")
    public ResponseEntity<MessageResponseDTO> updatePassword(
            @PathVariable Integer id,
            @RequestBody @Valid UpdatePasswordRequestDTO request) {
        authorService.updatePassword(id, request.getNewPassword());
        return ResponseEntity.ok(new MessageResponseDTO("Password updated successfully."));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessChecker.canAccessAuthor(#id)")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/works")
    @PreAuthorize("@accessChecker.canAccessAuthor(#request.authorId)")
    public ResponseEntity<WorkResponseDTO> submitWork(
            @RequestBody @Valid SubmitWorkRequestDTO request) {
        LiteraryWorksEntity work = new LiteraryWorksEntity();
        work.setTitle(request.getTitle());
        work.setAbout(request.getAbout());
        work.setPrice(request.getPrice());
        work.setCategory(request.getCategory());
        work.setContentType(request.getContentType());
        work.setGenre(request.getGenre());

        var author = authorService.getAuthor(request.getAuthorId());
        work.setAuthor(author);

        LiteraryWorksEntity saved = authorService.submitWork(work);
        return new ResponseEntity<>(mapToWorkDTO(saved), HttpStatus.CREATED);
    }

    @GetMapping("/works/{workId}")
    public ResponseEntity<WorkResponseDTO> getWork(@PathVariable Integer workId) {
        LiteraryWorksEntity work = authorService.getWork(workId);
        return ResponseEntity.ok(mapToWorkDTO(work));
    }

    private WorkResponseDTO mapToWorkDTO(LiteraryWorksEntity work) {
        WorkResponseDTO dto = new WorkResponseDTO();
        dto.setItemId(work.getItemId());
        dto.setTitle(work.getTitle());
        dto.setAbout(work.getAbout());
        dto.setPrice(work.getPrice());
        dto.setInStore(work.getInStore());
        dto.setCounts(work.getCounts());
        dto.setCategory(work.getCategory());
        dto.setContentType(work.getContentType());
        dto.setGenre(work.getGenre());
        if (work.getAuthor() != null) {
            dto.setAuthorId(work.getAuthor().getId());
            dto.setAuthorName(work.getAuthor().getName());
        }
        return dto;
    }
}