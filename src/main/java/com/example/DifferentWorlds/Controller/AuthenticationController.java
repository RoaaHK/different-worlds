package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.AdministratorEntity;
import com.example.DifferentWorlds.Entity.AuthorEntity;
import com.example.DifferentWorlds.Entity.CustomerEntity;
import com.example.DifferentWorlds.JWT.JwtUtils;
import com.example.DifferentWorlds.Repository.AdministratorRepository;
import com.example.DifferentWorlds.Repository.AuthorRepository;
import com.example.DifferentWorlds.Repository.CustomerRepository;
import com.example.DifferentWorlds.Service.AdminService;
import com.example.DifferentWorlds.Service.AuthenticationService;
import com.example.DifferentWorlds.Service.AuthorService;
import com.example.DifferentWorlds.Service.CustomerService;
import com.example.DifferentWorlds.dto.LoginRequestDTO;
import com.example.DifferentWorlds.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
// TODO class is to long
public class AuthenticationController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
   // private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final AdminService adminService;
    private final AdministratorRepository administratorRepository;
    private final JwtUtils jwtUtils;
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthenticationController(CustomerService customerService,
                                    //PasswordEncoder passwordEncoder,
                                    AuthenticationService authenticationService,
                                    CustomerRepository customerRepository,
                                    AuthenticationManager authenticationManager, AdminService adminService, AdministratorRepository administratorRepository,
                                    JwtUtils jwtUtils,
                                    AuthorService authorService,
                                    AuthorRepository authorRepository) {
        this.customerService = customerService;
        //this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
        this.customerRepository = customerRepository;
        this.authenticationManager = authenticationManager;
        this.adminService = adminService;
        this.administratorRepository = administratorRepository;
        this.jwtUtils=jwtUtils;
        this.authorService = authorService;
        this.authorRepository = authorRepository;
    }

    // TODO find a way to reduce redundant code.
    @PostMapping("/signup/customer")
    public ResponseEntity<?> signupCustomer(@RequestBody CustomerEntity customer) {
        authenticationService.signup(customer);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Customer registered successfully.");
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/signup/author")
    public ResponseEntity<?> signupAuthor(@RequestBody AuthorEntity author) {
        authenticationService.signup(author);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Author registered successfully.");
        return ResponseEntity.ok(responseDTO);
    }
    @PostMapping("/signup/admin")
    public ResponseEntity<?> signupAdmin(@RequestBody AdministratorEntity admin) {
        authenticationService.signup(admin);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Admin registered successfully.");
        return ResponseEntity.ok(responseDTO);
    }


    @PostMapping("/login/customer")
    // TODO try to avoid using ? unless it's a necessity
    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequestDTO loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                            loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password");
        }

        final UserDetails userDetails = customerService.loadUserByUsername(loginRequest.getUserName());
        final String jwt = jwtUtils.generateToken(userDetails);
        CustomerEntity customer = customerRepository.findByUserName(loginRequest.getUserName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return ResponseEntity.ok(new ResponseDTO(
                jwt,
                customer.getUserName(),
                customer.getPhoneNum(),
                customer.getId()
                ));
    }
    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequestDTO loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                            loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password");
        }

        final UserDetails userDetails = adminService.loadUserByUsername(loginRequest.getUserName());
        final String jwt = jwtUtils.generateToken(userDetails);
        AdministratorEntity admin = administratorRepository.findByUserName(loginRequest.getUserName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return ResponseEntity.ok(new ResponseDTO(
                jwt,
                admin.getUserName(),
                admin.getId()
        ));
    }
    @PostMapping("/login/author")
    public ResponseEntity<?> loginAuthor(@RequestBody LoginRequestDTO loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                            loginRequest.getPassword()));
            //        SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password");
        }

        final UserDetails userDetails = authorService.loadUserByUsername(loginRequest.getUserName());
        final String jwt = jwtUtils.generateToken(userDetails);
        AuthorEntity author = authorRepository.findByUserName(loginRequest.getUserName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return ResponseEntity.ok(new ResponseDTO(
                jwt,
                author.getUserName(),
                author.getId()
        ));
    }



    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        //String token = jwtUtils.extractUsername(authorizationHeader);
        String token = authorizationHeader.substring(7);
        jwtUtils.blacklistToken(token);
        return ResponseEntity.ok("Logged out successfully");
    }
}


// TODO remove unused method

//    @PostMapping("/logout")
//    public ResponseEntity<ResponseDTO> logout(@RequestHeader HttpServletRequest request) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            SecurityContextHolder.getContext().setAuthentication(null);
//        }
//        ResponseDTO responseDTO = new ResponseDTO();
//        responseDTO.setMessage("Logout successful.");
//        return ResponseEntity.ok(responseDTO);
//    }

//    @PostMapping("/login")
//    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO requestDTO) {
//        ResponseDTO responseDTO = new ResponseDTO();
//        if (requestDTO.getUserName().equals("Roaa")) {
//            responseDTO.setMessage("Login successful.");
//        } else {
//            responseDTO.setMessage("Login failed.");
//        }
//        return ResponseEntity.ok(responseDTO);
//    }

//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
//        try {
//            //authenticationService.authenticate();
//            //return ResponseEntity.ok("User login successfully");
//            String token = authenticationService.login(loginDTO);
//            return ResponseEntity.ok(new ResponseDTO(token));
//        } catch (IllegalArgumentException | BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage()+"11111");
//        }
//    }
//@PostMapping("/signup")
//public ResponseEntity<?> signup(@RequestBody Object request) {
//    if (request instanceof Customer) {
//        authenticationService.signup((Customer) request);
//    } else if (request instanceof Author) {
//        authenticationService.signup((Author) request);
//    } else {
//        return ResponseEntity.badRequest().body("Invalid request type.");
//    }
//    ResponseDTO responseDTO = new ResponseDTO();
//    responseDTO.setMessage("User registered successfully.");
//    return ResponseEntity.ok(responseDTO);
//}


