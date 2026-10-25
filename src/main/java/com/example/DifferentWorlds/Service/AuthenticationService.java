package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.AdministratorEntity;
import com.example.DifferentWorlds.Entity.AuthorEntity;
import com.example.DifferentWorlds.Entity.CustomerEntity;
import com.example.DifferentWorlds.Enums.Roles;
import com.example.DifferentWorlds.Exceptions.UsernameAlreadyExistsException;
import com.example.DifferentWorlds.Repository.AdministratorRepository;
import com.example.DifferentWorlds.Repository.AuthorRepository;
import com.example.DifferentWorlds.Repository.CustomerRepository;
import com.example.DifferentWorlds.dto.SignupRequestDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final AuthorRepository authorRepository;
    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(CustomerRepository customerRepository,
                                 AuthorRepository authorRepository,
                                 AdministratorRepository administratorRepository,
                                 PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.authorRepository = authorRepository;
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signupCustomer(SignupRequestDTO dto) {
        requireFreeUserName(dto.getUserName());
        CustomerEntity customer = new CustomerEntity();
        customer.setName(dto.getName());
        customer.setUserName(dto.getUserName());
        customer.setEmail(dto.getEmail());
        customer.setPassword(passwordEncoder.encode(dto.getPassword()));
        customer.setPhoneNum(dto.getPhoneNum());
        customer.setAge(dto.getAge());
        customer.setPoints(0);
        customer.setRole(Roles.Customer);
        customerRepository.save(customer);
    }

    @Transactional
    public void signupAuthor(SignupRequestDTO dto) {
        requireFreeUserName(dto.getUserName());
        AuthorEntity author = new AuthorEntity();
        author.setName(dto.getName());
        author.setUserName(dto.getUserName());
        author.setEmail(dto.getEmail());
        author.setPassword(passwordEncoder.encode(dto.getPassword()));
        author.setRole(Roles.Author);
        authorRepository.save(author);
    }

    @Transactional
    public void signupAdmin(SignupRequestDTO dto) {
        requireFreeUserName(dto.getUserName());
        AdministratorEntity admin = new AdministratorEntity();
        admin.setName(dto.getName());
        admin.setUserName(dto.getUserName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setRoles(Roles.Administrator);
        administratorRepository.save(admin);
    }


    private void requireFreeUserName(String userName) {
        boolean taken = authorRepository.findByUserName(userName).isPresent()
                || customerRepository.findByUserName(userName).isPresent()
                || administratorRepository.findByUserName(userName).isPresent();
        if (taken) {
            throw new UsernameAlreadyExistsException(userName);
        }
    }
}