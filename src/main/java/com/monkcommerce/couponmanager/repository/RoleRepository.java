package com.monkcommerce.couponmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monkcommerce.couponmanager.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
