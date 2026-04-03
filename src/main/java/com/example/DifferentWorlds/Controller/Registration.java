//package com.example.DifferentWorlds.Controller;
//import com.example.DifferentWorlds.Service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.example.DifferentWorlds.dto.UserDTO;
//
//@RestController
//@RequestMapping("/api/customers")
//public class Registration {
//
//    CustomerService customerService;
//    @Autowired
//    Registration(CustomerService customerService){
//        this.customerService=customerService;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerCustomer(@RequestBody UserDTO userDTO) {
//        try {
//            customerService.registerNewCustomer(userDTO);
//            return ResponseEntity.ok("Customer registered successfully");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed to register customer");
//        }
//    }
//}