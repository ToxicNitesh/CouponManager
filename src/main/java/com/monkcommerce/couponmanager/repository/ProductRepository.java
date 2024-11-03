package com.monkcommerce.couponmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monkcommerce.couponmanager.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
