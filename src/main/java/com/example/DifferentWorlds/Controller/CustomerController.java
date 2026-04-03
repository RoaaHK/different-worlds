package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.Customer;
import com.example.DifferentWorlds.Service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/customer")
public class CustomerController {

//     private EmailValidator validator;
//    CustomerController(){
//        this.validator=EmailValidator.getInstance();
//    }


    /// what HTTP Method is this RequestMapping ?
//    @RequestMapping("/about")
//    public String about() {
//        return "choose what life U want to live today";
//    }

    CustomerService customerService;
    @Autowired
    CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }

    @DeleteMapping("/deleteAccount")
    public void deleteAccount(@RequestBody Customer user) {
        customerService.deleteCustomer(user);
    }

    @GetMapping("/FindCustomerById/{customerID}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer customerID) {
        try {
            Customer customer = customerService.getCustomer(customerID);
            return ResponseEntity.ok(customer);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindCustomerByUserName/{userName}")
    public ResponseEntity<Customer> getCustomerByUserName(@PathVariable String userName) {
        try {
            Customer customer = customerService.getCustomerUserName(userName);
            return ResponseEntity.ok(customer);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    /// Use a DTO/Request Object
//    @PostMapping("/Customer")
//    public void addCustomer(@RequestBody Customer customer) {
//        customerService.addCustomer(customer);
//    }

    @PutMapping("/updateYourInfo/password")
    public void updatePassword(@RequestBody Customer user) {
        customerService.updatePassword(user);
    }

    @PutMapping("/updateYourInfo/email")
    public void updateEmail(@RequestBody Customer user) {
        customerService.updateEmail(user);
    }

//    @GetMapping("/me")
//    public ResponseEntity<Customer> authenticatedUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Customer currentUser = (Customer) authentication.getPrincipal();
//
//        return ResponseEntity.ok(currentUser);
//    }

    @GetMapping("/")
    public ResponseEntity<List<Customer>> allCustomers() {
        List<Customer> customers = customerService.allCustomers();

        return ResponseEntity.ok(customers);
    }

    /// variables then constructor then methods
    /// pass EmailValidator through constructor
    /// and remove @PostMapping("/sign up") from it


}
