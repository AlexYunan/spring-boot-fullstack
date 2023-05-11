package com.alexgiounan.springbootexample.repository;

import com.alexgiounan.springbootexample.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {


    boolean existsCustomerByEmail(String email);

    boolean existsCustomerById(Integer id);

}
