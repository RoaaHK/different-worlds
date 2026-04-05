package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.CustomerEntity;
import com.example.DifferentWorlds.Service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public void deleteAccount(@RequestBody CustomerEntity user) {
        customerService.deleteCustomer(user);
    }

    @GetMapping("/FindCustomerById/{customerID}")
    public ResponseEntity<CustomerEntity> getCustomerById(@PathVariable Integer customerID) {
        try {
            CustomerEntity customer = customerService.getCustomer(customerID);
            return ResponseEntity.ok(customer);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/FindCustomerByUserName/{userName}")
    public ResponseEntity<CustomerEntity> getCustomerByUserName(@PathVariable String userName) {
        try {
            CustomerEntity customer = customerService.getCustomerUserName(userName);
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
    public void updatePassword(@RequestBody CustomerEntity user) {
        customerService.updatePassword(user);
    }

    @PutMapping("/updateYourInfo/email")
    public void updateEmail(@RequestBody CustomerEntity user) {
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
    public ResponseEntity<List<CustomerEntity>> allCustomers() {
        List<CustomerEntity> customers = customerService.allCustomers();

        return ResponseEntity.ok(customers);
    }

    /// variables then constructor then methods
    /// pass EmailValidator through constructor
    /// and remove @PostMapping("/sign up") from it


}
