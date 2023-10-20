package org.example.service;

import org.example.domain.Customer;
import org.example.repository.CustomerRepository;
import org.example.web.rest.CustomerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer save(Customer customer){
        customer = customerRepository.save(customer);
        kafkaProducer.sendMessage("New customer created with id: " + customer.getId());
        return customer;
    }

    @Transactional(readOnly = true)
    public List<Customer> getCustomers(String name, String city, String state){
        Specification<Customer> spec = CustomerSpecification.getSpecification(name, city, state);
        return customerRepository.findAll(spec);
    }
}
