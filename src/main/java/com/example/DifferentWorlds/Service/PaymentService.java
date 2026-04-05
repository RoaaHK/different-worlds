package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.PaymentEntity;
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

    public PaymentEntity createPayment(PaymentEntity payment) {
        payment.setCreatedDate(LocalDateTime.now()); /// automatically
        return paymentRepository.save(payment);
    }

    public Optional<PaymentEntity> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Optional<PaymentEntity> getPaymentByUserName(String userName) {
        return paymentRepository.findByCustomerName(userName);
    }

    public List<PaymentEntity> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<PaymentEntity> getPaymentsBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findByCreatedDateBetween(startDate, endDate);
    }
}
