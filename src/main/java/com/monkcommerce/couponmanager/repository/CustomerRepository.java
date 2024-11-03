package com.monkcommerce.couponmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monkcommerce.couponmanager.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
