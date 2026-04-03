package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.Customer;
import com.example.DifferentWorlds.Entity.LiteraryWorks;
import com.example.DifferentWorlds.Entity.PurchaseHistory;
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

    public PurchaseHistory addPurchase(long customerId, long literaryWorkId, double purchasePrice) {
        Customer customer= customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        LiteraryWorks literaryWork= literaryWorksRepository.findById(literaryWorkId).orElseThrow(() -> new RuntimeException("LiteraryWork not found"));
        PurchaseHistory purchase=new PurchaseHistory();
        purchase.setCustomer(customer);
        purchase.setLiteraryWork(literaryWork);
        purchase.setPurchaseDate(new Date());
        purchase.setPurchasePrice(purchasePrice);
        return purchaseHistoryRepository.save(purchase);
    }

    public List<PurchaseHistory> getPurchaseHistory(Integer customerId) {
        return purchaseHistoryRepository.findByCustomerId(customerId);
    }
}
