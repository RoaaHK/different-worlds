package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.CartEntity;
import com.example.DifferentWorlds.Entity.CustomerEntity;
import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import com.example.DifferentWorlds.Entity.PaymentEntity;
import com.example.DifferentWorlds.Entity.PurchaseHistoryEntity;
import com.example.DifferentWorlds.Enums.PaymentMethod;
import com.example.DifferentWorlds.Exceptions.EmptyCartException;
import com.example.DifferentWorlds.Exceptions.ItemNotAvailableException;
import com.example.DifferentWorlds.Repository.CustomerRepository;
import com.example.DifferentWorlds.Repository.PurchaseHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final CustomerRepository customerRepository;
    private final CartService cartService;
    private final StoreService storeService;
    private final PaymentService paymentService;

    @Autowired
    public PurchaseService(
            PurchaseHistoryRepository purchaseHistoryRepository,
            CustomerRepository customerRepository,
            CartService cartService,
            StoreService storeService,
            PaymentService paymentService) {
        this.purchaseHistoryRepository = purchaseHistoryRepository;
        this.customerRepository = customerRepository;
        this.cartService = cartService;
        this.storeService = storeService;
        this.paymentService = paymentService;
    }

    @Transactional
    public CheckoutResult checkout(Integer customerId, PaymentMethod paymentMethod) {
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Customer not found with ID: " + customerId));

        List<CartEntity> cart = cartService.getCart(customerId);
        if (cart.isEmpty()) {
            throw new EmptyCartException();
        }

        List<PurchaseHistoryEntity> purchases = new ArrayList<>();
        double total = 0;
        for (CartEntity line : cart) {
            LiteraryWorksEntity work = line.getLiteraryWork();
            int quantity = line.getQuantity();

            if (!Boolean.TRUE.equals(work.getInStore())) {
                throw new ItemNotAvailableException(work.getTitle());
            }
            storeService.decreaseStock(work.getItemId(), quantity);

            PurchaseHistoryEntity purchase = new PurchaseHistoryEntity();
            purchase.setCustomer(customer);
            purchase.setLiteraryWork(work);
            purchase.setQuantity(quantity);
            purchase.setPurchasePrice(work.getPrice());
            purchases.add(purchaseHistoryRepository.save(purchase));

            total += work.getPrice() * quantity;
        }

        PaymentEntity payment = new PaymentEntity();
        payment.setCustomer(customer);
        payment.setAmount(Math.round(total * 100) / 100.0);
        payment.setPaymentMethod(paymentMethod);
        PaymentEntity savedPayment = paymentService.createPayment(payment);

        cartService.clearCart(customerId);

        return new CheckoutResult(purchases, savedPayment);
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