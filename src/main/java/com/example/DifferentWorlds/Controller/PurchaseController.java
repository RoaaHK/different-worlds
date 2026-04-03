package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.PurchaseHistory;
import com.example.DifferentWorlds.Service.PurchaseService;
import com.example.DifferentWorlds.dto.PurchaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseHistory> addPurchase(@RequestBody PurchaseRequest request) {
        PurchaseHistory purchase= purchaseService.addPurchase(request.getCustomerId(), request.getLiteraryWorkId(), request.getPurchasePrice());
        return new ResponseEntity <>(purchase, HttpStatus.CREATED);
    }

    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<PurchaseHistory>> getPurchaseHistory(@PathVariable Integer customerId) {
        List<PurchaseHistory> history = purchaseService.getPurchaseHistory(customerId);
        return new ResponseEntity <>(history, HttpStatus.OK);
    }
}
