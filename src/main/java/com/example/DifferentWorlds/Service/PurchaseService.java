package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.CustomerEntity;
import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import com.example.DifferentWorlds.Entity.PurchaseHistoryEntity;
import com.example.DifferentWorlds.Repository.CustomerRepository;
import com.example.DifferentWorlds.Repository.LiteraryWorksRepository;
import com.example.DifferentWorlds.Repository.PurchaseHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final CustomerRepository customerRepository;
    private final LiteraryWorksRepository literaryWorksRepository;

    @Autowired
    public PurchaseService(
    PurchaseHistoryRepository purchaseHistoryRepository,
    CustomerRepository customerRepository,
    LiteraryWorksRepository literaryWorksRepository){
        this.purchaseHistoryRepository=purchaseHistoryRepository;
        this.customerRepository=customerRepository;
        this.literaryWorksRepository=literaryWorksRepository;
    }

    @Transactional
    public PurchaseHistoryEntity addPurchase(Integer customerId, Integer literaryWorkId, double purchasePrice) {
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Customer not found with ID: " + customerId));

        LiteraryWorksEntity literaryWork = literaryWorksRepository.findById(literaryWorkId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Literary work not found with ID: " + literaryWorkId));

        PurchaseHistoryEntity purchase = new PurchaseHistoryEntity();
        purchase.setCustomer(customer);
        purchase.setLiteraryWork(literaryWork);
        purchase.setPurchasePrice(purchasePrice);

        return purchaseHistoryRepository.save(purchase);
    }

    @Transactional(readOnly = true)
    public List<PurchaseHistoryEntity> getPurchaseHistory(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(
                    "Customer not found with ID: " + customerId);
        }
        return purchaseHistoryRepository.findByCustomer_Id(customerId);
    }
}
