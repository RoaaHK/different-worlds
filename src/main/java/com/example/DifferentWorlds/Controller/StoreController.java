package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import com.example.DifferentWorlds.Service.AuthorService;
import com.example.DifferentWorlds.Service.StoreService;
import com.example.DifferentWorlds.dto.AddItemRequestDTO;
import com.example.DifferentWorlds.dto.MessageResponseDTO;
import com.example.DifferentWorlds.dto.WorkResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;
    private final AuthorService authorService;

    public StoreController(StoreService storeService, AuthorService authorService) {
        this.storeService = storeService;
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkResponseDTO> getItemById(@PathVariable Integer id) {
        LiteraryWorksEntity item = storeService.getItem(id);
        return ResponseEntity.ok(mapToDTO(item));
    }

    // GET /api/store
    @GetMapping
    public ResponseEntity<List<WorkResponseDTO>> getAllItems() {
        List<WorkResponseDTO> items = storeService.getAllItem()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<WorkResponseDTO> addItem(@RequestBody @Valid AddItemRequestDTO request) {
        LiteraryWorksEntity item = new LiteraryWorksEntity();
        item.setTitle(request.getTitle());
        item.setAbout(request.getAbout());
        item.setPrice(request.getPrice());
        item.setInStore(request.getInStore());
        item.setCounts(request.getCounts());
        item.setCategory(request.getCategory());
        item.setContentType(request.getContentType());
        item.setGenre(request.getGenre());
        item.setAuthor(authorService.getAuthor(request.getAuthorId()));

        storeService.addItem(item);

        LiteraryWorksEntity saved = storeService.getItem(
                item.getItemId() != null ? item.getItemId() :
                        // fallback: find by title (addItem checks uniqueness so it's safe)
                        storeService.getAllItem().stream()
                                .filter(i -> i.getTitle().equals(item.getTitle()))
                                .findFirst()
                                .orElse(item)
                                .getItemId()
        );
        return new ResponseEntity<>(mapToDTO(saved), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer id) {
        storeService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/stock/increase")
    public ResponseEntity<MessageResponseDTO> increaseStock(
            @PathVariable Integer id,
            @RequestParam int delta) {
        storeService.increaseStock(id, delta);
        return ResponseEntity.ok(new MessageResponseDTO("Stock increased by " + delta + "."));
    }

    @PatchMapping("/{id}/stock/decrease")
    public ResponseEntity<MessageResponseDTO> decreaseStock(
            @PathVariable Integer id,
            @RequestParam int delta) {
        storeService.decreaseStock(id, delta);
        return ResponseEntity.ok(new MessageResponseDTO("Stock decreased by " + delta + "."));
    }

    @PatchMapping("/{id}/discount")
    public ResponseEntity<MessageResponseDTO> applyDiscount(
            @PathVariable Integer id,
            @RequestParam double discountPct) {
        storeService.applyDiscount(id, discountPct);
        return ResponseEntity.ok(new MessageResponseDTO("Discount applied."));
    }

    private WorkResponseDTO mapToDTO(LiteraryWorksEntity item) {
        WorkResponseDTO dto = new WorkResponseDTO();
        dto.setItemId(item.getItemId());
        dto.setTitle(item.getTitle());
        dto.setAbout(item.getAbout());
        dto.setPrice(item.getPrice());
        dto.setInStore(item.getInStore());
        dto.setCounts(item.getCounts());
        dto.setCategory(item.getCategory());
        dto.setContentType(item.getContentType());
        dto.setGenre(item.getGenre());
        if (item.getAuthor() != null) {
            dto.setAuthorId(item.getAuthor().getId());
            dto.setAuthorName(item.getAuthor().getName());
        }
        return dto;
    }
}