package com.monkcommerce.couponmanager.service;

import java.util.List;
import java.util.Map;

import com.monkcommerce.couponmanager.dto.Cart;
import com.monkcommerce.couponmanager.dto.CouponDto;
import com.monkcommerce.couponmanager.entity.Coupon;

public interface CouponService {

	public List<Coupon> getAllCoupons();
	
	public Map<String,String> createCoupon(CouponDto coupon);

	public Coupon getCouponById(Long id);

	public Map<String, String> deleteCoupon(Long id);

	public List<CouponDto> getApplicableCoupons(Cart cart,Long couponId);
}
