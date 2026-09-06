package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.PaymentEntity;
import com.example.DifferentWorlds.Repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public PaymentEntity createPayment(PaymentEntity payment) {
        ///payment.setCreatedDate(LocalDateTime.now()); /// automatically
        return paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public PaymentEntity getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Payment not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public PaymentEntity getPaymentByCustomerUserName(String userName) {
        return paymentRepository.findByCustomer_UserName(userName)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Payment not found for customer: " + userName));
    }

    @Transactional(readOnly = true)
    public List<PaymentEntity> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<PaymentEntity> getPaymentsBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findByCreatedDateBetween(startDate, endDate);
    }
}
