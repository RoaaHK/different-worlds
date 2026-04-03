package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.Payment;
import com.example.DifferentWorlds.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(Payment payment) {
        payment.setCreatedDate(LocalDateTime.now()); /// automatically
        return paymentRepository.save(payment);
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Optional<Payment> getPaymentByUserName(String userName) {
        return paymentRepository.findByCustomerName(userName);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findByCreatedDateBetween(startDate, endDate);
    }
}
