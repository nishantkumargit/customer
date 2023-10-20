package org.example.web.rest;

import jakarta.persistence.criteria.Predicate;
import org.example.domain.Customer;
import org.example.repository.CustomerRepository;
import org.example.service.CustomerService;
import org.example.service.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/customer")
public class CustomerResource {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) String city,
                                          @RequestParam(required = false) String state) {
        return customerService.getCustomers(name, city, state);
    }

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.save(customer);
        return savedCustomer;
    }

}
