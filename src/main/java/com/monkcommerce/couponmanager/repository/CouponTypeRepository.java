package com.monkcommerce.couponmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monkcommerce.couponmanager.entity.CouponType;

public interface CouponTypeRepository extends JpaRepository<CouponType, Long>{

	public CouponType findByCouponType(String type);

}
