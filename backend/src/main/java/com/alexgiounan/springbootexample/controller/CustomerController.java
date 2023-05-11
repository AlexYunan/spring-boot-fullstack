package com.alexgiounan.springbootexample.controller;

import com.alexgiounan.springbootexample.customer.Customer;
import com.alexgiounan.springbootexample.customer.CustomerRegistrationRequest;
import com.alexgiounan.springbootexample.customer.CustomerUpdateRequest;
import com.alexgiounan.springbootexample.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable("id") Integer customerId){
        return customerService.getCustomer(customerId);
    }

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request){
        customerService.addCustomer(request);
    }

    @DeleteMapping("{id}")
    public void deleteCustomer(@PathVariable("id") Integer customerId){
        customerService.deleteCustomerById(customerId);
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable("id") Integer customerId,
                               @RequestBody CustomerUpdateRequest updateRequest){
        customerService.updateCustomer(customerId,updateRequest);

    }
}
