package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.Customer;
import com.example.DifferentWorlds.Repository.CustomerRepository;
import com.example.DifferentWorlds.dto.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Autowired
    CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder=passwordEncoder;
        this.customerRepository = customerRepository;
    }

    @Autowired
    private Validator validator;

    @Transactional
    public void registerNewCustomer(UserDTO userDTO) {

        Customer customer = new Customer();
        customer.setEmail(userDTO.getEmail());
        customer.setPassword(userDTO.getPassword());

        customerRepository.save(customer);
    }

        @Transactional
    public void registerNewCustomer(String name, String email, String pass){

    }
    // don't use @NotNull, check of null val in controller
    public void updateEmail(Customer user) {
        Optional<Customer> customer = customerRepository.findByUserName(user.getUserName());
        customer.get().setEmail(user.getEmail());
        customerRepository.save(customer.get());
    }

    public void updatePassword(Customer user) {
        Optional<Customer> customer = customerRepository.findByUserName(user.getUserName());
        customer.get().setPassword(user.getPassword());
        customerRepository.save(customer.get());
    }

    public Customer getCustomer(long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new EntityNotFoundException("Customer not found with ID: " + customerId);
        }
        return customerOptional.get();
    }

    public Customer getCustomerUserName(String userName) {
        Optional<Customer> customerOptional = customerRepository.findByUserName(userName);
        if (customerOptional.isEmpty()) {
            throw new EntityNotFoundException("Customer not found with ID: " + userName);
        }
        return customerOptional.get();
    }

    public void deleteCustomer(Customer customer) {
        if (customerRepository.findByUserName(customer.getUserName()).isPresent()) {
            customerRepository.delete(customer);
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(customer.getUserName(), customer.getPassword(),
                new ArrayList<>());
    }

    public List<Customer> allCustomers() {
        List<Customer> customers = new ArrayList<>();

        customerRepository.findAll().forEach(customers::add);

        return customers;
    }
}

