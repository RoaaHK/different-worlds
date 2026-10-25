package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.AdministratorEntity;
import com.example.DifferentWorlds.Entity.AuthorEntity;
import com.example.DifferentWorlds.Entity.CustomerEntity;
import com.example.DifferentWorlds.JWT.JwtService;
import com.example.DifferentWorlds.Service.AdminService;
import com.example.DifferentWorlds.Service.AuthenticationService;
import com.example.DifferentWorlds.Service.AuthorService;
import com.example.DifferentWorlds.Service.CustomerService;
import com.example.DifferentWorlds.UserDetails.CustomUserDetailsService;
import com.example.DifferentWorlds.dto.LoginRequestDTO;
import com.example.DifferentWorlds.dto.LoginResponseDTO;
import com.example.DifferentWorlds.dto.SignupRequestDTO;
import com.example.DifferentWorlds.dto.SignupResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomerService customerService;
    private final AdminService adminService;
    private final AuthorService authorService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthenticationController(AuthenticationService authenticationService,
                                    AuthenticationManager authenticationManager,
                                    JwtService jwtService,
                                    CustomerService customerService,
                                    AdminService adminService,
                                    AuthorService authorService,
                                    CustomUserDetailsService customUserDetailsService) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customerService = customerService;
        this.adminService = adminService;
        this.authorService = authorService;
        this.customUserDetailsService = customUserDetailsService;
    }
    @PostMapping("/signup/customer")
    public ResponseEntity<SignupResponseDTO> signupCustomer(
            @RequestBody @Valid SignupRequestDTO request) {
        authenticationService.signupCustomer(request);
        return ResponseEntity.ok(new SignupResponseDTO("Customer registered successfully."));
    }

    @PostMapping("/signup/author")
    public ResponseEntity<SignupResponseDTO> signupAuthor(
            @RequestBody @Valid SignupRequestDTO request) {
        authenticationService.signupAuthor(request);
        return ResponseEntity.ok(new SignupResponseDTO("Author registered successfully."));
    }
    @PostMapping("/signup/admin")
    public ResponseEntity<SignupResponseDTO> signupAdmin(
            @RequestBody @Valid SignupRequestDTO request) {
        authenticationService.signupAdmin(request);
        return ResponseEntity.ok(new SignupResponseDTO("Admin registered successfully."));
    }

    @PostMapping("/login/customer")
    public ResponseEntity<LoginResponseDTO> loginCustomer(
            @RequestBody @Valid LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Incorrect username or password");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUserName());
        String jwt = jwtService.generateToken(userDetails);
        CustomerEntity customer = customerService.getCustomerByUserName(request.getUserName());

        return ResponseEntity.ok(new LoginResponseDTO(
                jwt,
                customer.getUserName(),
                customer.getId().longValue(),
                "CUSTOMER"
        ));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<LoginResponseDTO> loginAdmin(
            @RequestBody @Valid LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Incorrect username or password");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUserName());
        String jwt = jwtService.generateToken(userDetails);
        AdministratorEntity admin = adminService.getAdminByUserName(request.getUserName());

        return ResponseEntity.ok(new LoginResponseDTO(
                jwt,
                admin.getUserName(),
                admin.getId().longValue(),
                "ADMIN"
        ));
    }

    @PostMapping("/login/author")
    public ResponseEntity<LoginResponseDTO> loginAuthor(
            @RequestBody @Valid LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Incorrect username or password");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUserName());
        String jwt = jwtService.generateToken(userDetails);
        AuthorEntity author = authorService.getAuthorByUserName(request.getUserName());

        return ResponseEntity.ok(new LoginResponseDTO(
                jwt,
                author.getUserName(),
                author.getId().longValue(),
                "AUTHOR"
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<SignupResponseDTO> logout(
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        jwtService.blacklistToken(token);
        return ResponseEntity.ok(new SignupResponseDTO("Logged out successfully."));
    }
}