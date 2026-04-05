package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.CustomerEntity;
import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import com.example.DifferentWorlds.Entity.PurchaseHistoryEntity;
import com.example.DifferentWorlds.Repository.CustomerRepository;
import com.example.DifferentWorlds.Repository.PurchaseHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.DifferentWorlds.Repository.ItemRepository;

import java.util.Date;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository literaryWorksRepository;

    @Autowired
    public PurchaseService(
    PurchaseHistoryRepository purchaseHistoryRepository,
    CustomerRepository customerRepository,
    ItemRepository literaryWorksRepository){
        this.purchaseHistoryRepository=purchaseHistoryRepository;
        this.customerRepository=customerRepository;
        this.literaryWorksRepository=literaryWorksRepository;
    }

    public PurchaseHistoryEntity addPurchase(long customerId, long literaryWorkId, double purchasePrice) {
        CustomerEntity customer= customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        LiteraryWorksEntity literaryWork= literaryWorksRepository.findById(literaryWorkId).orElseThrow(() -> new RuntimeException("LiteraryWork not found"));
        PurchaseHistoryEntity purchase=new PurchaseHistoryEntity();
        purchase.setCustomer(customer);
        purchase.setLiteraryWork(literaryWork);
        purchase.setPurchaseDate(new Date());
        purchase.setPurchasePrice(purchasePrice);
        return purchaseHistoryRepository.save(purchase);
    }

    public List<PurchaseHistoryEntity> getPurchaseHistory(Integer customerId) {
        return purchaseHistoryRepository.findByCustomerId(customerId);
    }
}
