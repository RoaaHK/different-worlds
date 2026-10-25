package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import com.example.DifferentWorlds.Exceptions.InsufficientStockException;
import com.example.DifferentWorlds.Exceptions.ItemNotAvailableException;
import com.example.DifferentWorlds.Exceptions.WorkAlreadyExistsException;
import com.example.DifferentWorlds.Repository.LiteraryWorksRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreService {
    private final LiteraryWorksRepository literaryWorksRepository;

    @Autowired
    public StoreService(LiteraryWorksRepository literaryWorksRepository) {
        this.literaryWorksRepository = literaryWorksRepository;
    }

    @Transactional(readOnly = true)
    public LiteraryWorksEntity getItem(Integer itemId) {
        return literaryWorksRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Literary work not found with ID: " + itemId));
    }

//    @Transactional(readOnly = true)
//    public List<LiteraryWorksEntity> getAllItems() {
//        return literaryWorksRepository.findByApprovalStatus(LiteraryWorkStatus.APPROVED);
//    }
//
//    @Transactional(readOnly = true)
//    public List<LiteraryWorksEntity> getPendingItems() {
//        return literaryWorksRepository.findByApprovalStatus(LiteraryWorkStatus.PENDING);
//    }

    public List<LiteraryWorksEntity> getAllItem() {
        return literaryWorksRepository.findAll();
    }

    @Transactional
    public void addItem(LiteraryWorksEntity item) {
        if (literaryWorksRepository.findByTitle(item.getTitle()).isPresent()) {
            throw new WorkAlreadyExistsException(item.getTitle());
        }
        literaryWorksRepository.save(item);
    }

    @Transactional
    public void deleteItem(Integer itemId) {
        if (!literaryWorksRepository.existsById(itemId)) {
            throw new EntityNotFoundException(
                    "Literary work not found with ID: " + itemId);
        }
        literaryWorksRepository.deleteById(itemId);
    }

    @Transactional
    public void increaseStock(Integer itemId, int delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException(
                    "Delta must be positive, got: " + delta);
        }
        LiteraryWorksEntity item = literaryWorksRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Literary work not found with ID: " + itemId));
        item.setCounts(item.getCounts() + delta);
        literaryWorksRepository.save(item);
    }

    @Transactional
    public void decreaseStock(Integer itemId, int delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException(
                    "Delta must be positive, got: " + delta);
        }
        LiteraryWorksEntity item = literaryWorksRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Literary work not found with ID: " + itemId));
        if (item.getCounts() < delta) {
            throw new InsufficientStockException(item.getCounts(), delta);
        }
        item.setCounts(item.getCounts() - delta);
        literaryWorksRepository.save(item);
    }

    @Transactional
    public void applyDiscount(Integer itemId, double discountPct) {
        if (discountPct < 0.0 || discountPct > 1.0) {
            throw new IllegalArgumentException(
                    "Discount must be between 0.0 and 1.0, got: " + discountPct);
        }
        LiteraryWorksEntity item = literaryWorksRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Literary work not found with ID: " + itemId));
        if (!item.getInStore()) {
            throw new ItemNotAvailableException(item.getTitle());
        }
        item.setPrice(item.getPrice() * (1.0 - discountPct));
        literaryWorksRepository.save(item);
    }

//    public void addItem(String name, String about, double price, boolean inStore,
//                        ContentTypes contentType, NonFictionGenres nonFictionGenre,
//                        FictionGenres fictionGenre, AuthorEntity author) {
//        // TODO extrtact into another method
//        LiteraryWorksEntity item = new LiteraryWorksEntity();
//        item.setTitle(name);
//        item.setAbout(about);
//        item.setPrice(price);
//        item.setInStore(inStore);
//        item.setContentType(contentType);
//        item.setNonFictionGenre(nonFictionGenre);
//        item.setFictionGenre(fictionGenre);
//        item.setAuthor(author);
//        //item.setApprovedByAdmin(false);
//        literaryWorksRepository.save(item);
//    }
}