package com.example.DifferentWorlds.UserDetails;

import com.example.DifferentWorlds.Repository.AdministratorRepository;
import com.example.DifferentWorlds.Repository.AuthorRepository;
import com.example.DifferentWorlds.Repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final AuthorRepository authorRepository;
    private final CustomerRepository customerRepository;
    private final AdministratorRepository administratorRepository;

    @Autowired
    public CustomUserDetailsService(AuthorRepository authorRepository,
                                    CustomerRepository customerRepository,
                                    AdministratorRepository administratorRepository) {
        this.authorRepository = authorRepository;
        this.customerRepository = customerRepository;
        this.administratorRepository = administratorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Searching for user with username: {}", username);

        return authorRepository.findByUserName(username)
                .map(author -> buildUserDetails(author.getId(), author.getUserName(), author.getPassword(), "AUTHOR"))
                .or(() -> customerRepository.findByUserName(username)
                        .map(customer -> buildUserDetails(customer.getId(), customer.getUserName(), customer.getPassword(), "CUSTOMER")))
                .or(() -> administratorRepository.findByUserName(username)
                        .map(admin -> buildUserDetails(admin.getId(), admin.getUserName(), admin.getPassword(), "ADMIN")))
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });
    }

    private UserDetails buildUserDetails(Integer id, String userName, String password, String role) {
        return new CustomUserDetails(
                id,
                userName,
                password,
                List.of(new SimpleGrantedAuthority("ROLE_" + role)),
                role);
    }
}