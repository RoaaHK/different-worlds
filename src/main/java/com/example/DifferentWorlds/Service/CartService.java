package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.CartEntity;
import com.example.DifferentWorlds.Entity.CustomerEntity;
import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import com.example.DifferentWorlds.Repository.CartRepository;
import com.example.DifferentWorlds.Repository.CustomerRepository;
import com.example.DifferentWorlds.Repository.LiteraryWorksRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final LiteraryWorksRepository literaryWorksRepository;

    @Autowired
    public CartService(CartRepository cartRepository,
                       CustomerRepository customerRepository,
                       LiteraryWorksRepository literaryWorksRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.literaryWorksRepository = literaryWorksRepository;
    }

    @Transactional(readOnly = true)
    public List<CartEntity> getCart(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(
                    "Customer not found with ID: " + customerId);
        }
        return cartRepository.findByCustomer_Id(customerId);
    }

    @Transactional
    public CartEntity addToCart(Integer customerId, Integer itemId, int quantity) {
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Customer not found with ID: " + customerId));

        LiteraryWorksEntity work = literaryWorksRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Literary work not found with ID: " + itemId));

        // TODO: uncomment once approval workflow is implemented
        // if (work.getApprovalStatus() != LiteraryWorkStatus.APPROVED) {
        //     throw new IllegalStateException("This literary work is not available in the store");
        // }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be positive, got: " + quantity);
        }

        // If this work is already in the customer's cart → increase quantity
        // Otherwise → create a new cart entry
        return cartRepository
                .findByCustomer_IdAndLiteraryWork_ItemId(customerId, itemId)
                .map(existingEntry -> {
                    existingEntry.setQuantity(existingEntry.getQuantity() + quantity);
                    return cartRepository.save(existingEntry);
                })
                .orElseGet(() -> {
                    CartEntity newEntry = new CartEntity();
                    newEntry.setCustomer(customer);
                    newEntry.setLiteraryWork(work);
                    newEntry.setQuantity(quantity);
                    return cartRepository.save(newEntry);
                });
    }

    @Transactional
    public void removeFromCart(Integer customerId, Integer itemId) {
        CartEntity entry = cartRepository
                .findByCustomer_IdAndLiteraryWork_ItemId(customerId, itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "This item is not in the cart"));
        cartRepository.delete(entry);
    }

    @Transactional
    public void updateQuantity(Integer customerId, Integer itemId, int newQuantity) {
        if (newQuantity <= 0) {
            removeFromCart(customerId, itemId);
            return;
        }
        CartEntity entry = cartRepository
                .findByCustomer_IdAndLiteraryWork_ItemId(customerId, itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "This item is not in the cart"));
        entry.setQuantity(newQuantity);
        cartRepository.save(entry);
    }

    @Transactional
    public void clearCart(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(
                    "Customer not found with ID: " + customerId);
        }
        cartRepository.deleteByCustomer_Id(customerId);
    }
}