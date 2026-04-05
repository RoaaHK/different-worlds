package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.AdministratorEntity;
import com.example.DifferentWorlds.Entity.AuthorEntity;
import com.example.DifferentWorlds.Entity.CustomerEntity;
import com.example.DifferentWorlds.JWT.JwtUtils;
import com.example.DifferentWorlds.Repository.AdministratorRepository;
import com.example.DifferentWorlds.Repository.AuthorRepository;
import com.example.DifferentWorlds.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdministratorRepository administratorRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
@Autowired
    public AuthenticationService(CustomerRepository customerRepository,
                                 AuthorRepository authorRepository,
                                 PasswordEncoder passwordEncoder, AdministratorRepository administratorRepository,
                                 AuthenticationManager authenticationManager,
                                 JwtUtils jwtUtils){
    this.administratorRepository = administratorRepository;
    this.authenticationManager=authenticationManager;
        this.customerRepository=customerRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authorRepository=authorRepository;
    }

    public void signup(CustomerEntity newCustomer) {
        if (customerRepository.findByUserName(newCustomer.getUserName()).isPresent()) {
            throw new IllegalArgumentException("User already exists with this email");
        }
        newCustomer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
        customerRepository.save(newCustomer);
    }

    public void signup(AuthorEntity newAuthor) {
        if (authorRepository.findByUserName(newAuthor.getUserName()).isPresent()) {
            throw new IllegalArgumentException("User already exists with this email");
        }
        newAuthor.setPassword(passwordEncoder.encode(newAuthor.getPassword()));
        authorRepository.save(newAuthor);
    }
    public void signup(AdministratorEntity newAdmin) {
        if (authorRepository.findByUserName(newAdmin.getUserName()).isPresent()) {
            throw new IllegalArgumentException("User already exists with this email");
        }
        newAdmin.setPassword(passwordEncoder.encode(newAdmin.getPassword()));
        administratorRepository.save(newAdmin);
    }
}
//
//    public String login (LoginDTO loginDTO) {
//        Customer customer = customerRepository.findByEmail(loginDTO.getEmail())
//                .orElseThrow(
//                        () -> new IllegalArgumentException("User doesn't exist"));
//        //loginDTO.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
////        if(!passwordEncoder.matches(loginDTO.getPassword(),customer.getPassword())){
////            throw new BadCredentialsException("invalid password2222222");
////        }
//         return jwtUtils.generateToken(customer);
//}

//    public Customer authenticate(LoginDTO input) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        input.getEmail(),
//                        input.getPassword()
//                )
//        );
//        return customerRepository.findByEmail(input.getEmail())
//                .orElseThrow();
//    }

