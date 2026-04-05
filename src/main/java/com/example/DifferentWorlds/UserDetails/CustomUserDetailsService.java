package com.example.DifferentWorlds.UserDetails;

import com.example.DifferentWorlds.Entity.AdministratorEntity;
import com.example.DifferentWorlds.Entity.AuthorEntity;
import com.example.DifferentWorlds.Entity.CustomerEntity;
import com.example.DifferentWorlds.Repository.AdministratorRepository;
import com.example.DifferentWorlds.Repository.AuthorRepository;
import com.example.DifferentWorlds.Repository.CustomerRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final AuthorRepository authorRepository;
    private final CustomerRepository customerRepository;
    private final AdministratorRepository administratorRepository;

    @Autowired
    public CustomUserDetailsService(AuthorRepository authorRepository, CustomerRepository customerRepository, AdministratorRepository administratorRepository) {
        this.authorRepository = authorRepository;
        this.customerRepository = customerRepository;
        this.administratorRepository = administratorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Searching for user with username: {}", username);
        /// Check if the user is an author
        AuthorEntity author = authorRepository.findByUserName(username).orElse(null);
        if (author != null) {
            return new CustomUserDetails(author.getUserName(), author.getPassword(), "Author");
        }

        /// Check if the user is a customer
        CustomerEntity customer = customerRepository.findByUserName(username).orElse(null);
        if (customer != null) {
            return new CustomUserDetails(customer.getUserName(), customer.getPassword(), "Customer");
        }
        /// Check if the user is an admin
        AdministratorEntity admin = administratorRepository.findByUserName(username).orElse(null);
        if (admin != null) {
            return new CustomUserDetails(admin.getUserName(), admin.getPassword(), "Administrator");
        }
        logger.error("User not found with username: {}", username);
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
