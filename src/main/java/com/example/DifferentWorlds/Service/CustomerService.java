package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.CustomerEntity;
import com.example.DifferentWorlds.Repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Autowired
    CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder=passwordEncoder;
        this.customerRepository = customerRepository;
    }

    // don't use @NotNull, check of null val in controller

    @Transactional(readOnly = true)
    public CustomerEntity getCustomer(Integer customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Customer not found with ID: " + customerId));
    }

    @Transactional(readOnly = true)
    public CustomerEntity getCustomerByUserName(String userName) {
        return customerRepository.findByUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Customer not found with username: " + userName));
    }

    @Transactional(readOnly = true)
    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public void updateEmail(Integer customerId, String newEmail) {
        CustomerEntity existing = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Customer not found with ID: " + customerId));
        existing.setEmail(newEmail);
        customerRepository.save(existing);
    }

    @Transactional
    public void updatePassword(Integer customerId, String newRawPassword) {
        CustomerEntity existing = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Customer not found with ID: " + customerId));
        existing.setPassword(passwordEncoder.encode(newRawPassword));
        customerRepository.save(existing);
    }

    @Transactional
    public void deleteCustomer(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(
                    "Customer not found with ID: " + customerId);
        }
        customerRepository.deleteById(customerId);
    }
}