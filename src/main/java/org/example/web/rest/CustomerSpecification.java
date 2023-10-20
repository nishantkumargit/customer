package org.example.web.rest;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.example.domain.Address;
import org.example.domain.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerSpecification {

    public static Specification<Customer> getSpecification(String fullName, String city, String state){

        Specification<Customer> spec = Specification.where(null);

        if (fullName != null) {
            spec = spec.and(CustomerSpecification.hasFullName(fullName));
        }

        if (city != null) {
            spec = spec.and(CustomerSpecification.hasAddressCity(city));
        }

        if (state != null) {
            spec = spec.and(CustomerSpecification.hasAddressState(state));
        }

        return spec;
    }

    public static Specification<Customer> hasAddressCity(String city) {
        return (root, query, criteriaBuilder) -> {
            Join<Customer, Address> addressJoin = root.join("addresses");
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(addressJoin.get("city"), city));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Customer> hasAddressState(String state) {
        return (root, query, criteriaBuilder) -> {
            Join<Customer, Address> addressJoin = root.join("addresses");
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(addressJoin.get("state"), state));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Customer> hasFullName(String fullName) {
        return (root, query, criteriaBuilder) -> {
            String[] names = fullName.split("\\s+");
            Predicate firstNamePredicate = criteriaBuilder.equal(root.get("firstName"), names[0]);
            if(names.length > 1){
                return criteriaBuilder.and(firstNamePredicate, criteriaBuilder.equal(root.get("lastName"), names.length > 1 ? names[1] : ""));
            }
            return criteriaBuilder.and(firstNamePredicate);
        };
    }

}
