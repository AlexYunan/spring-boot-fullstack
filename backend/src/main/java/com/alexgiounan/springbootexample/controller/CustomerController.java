package com.alexgiounan.springbootexample.controller;

import com.alexgiounan.springbootexample.customer.Customer;
import com.alexgiounan.springbootexample.customer.CustomerRegistrationRequest;
import com.alexgiounan.springbootexample.customer.CustomerUpdateRequest;
import com.alexgiounan.springbootexample.dto.CustomerDTO;
import com.alexgiounan.springbootexample.jwt.JWTUtil;
import com.alexgiounan.springbootexample.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private final JWTUtil jwtUtil;

    @GetMapping()
    public List<CustomerDTO> getCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable("id") Integer customerId){
        return customerService.getCustomer(customerId);
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationRequest request){
        customerService.addCustomer(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtToken).build();
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
