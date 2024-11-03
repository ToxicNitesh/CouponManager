package com.monkcommerce.couponmanager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monkcommerce.couponmanager.dto.Cart;
import com.monkcommerce.couponmanager.dto.CouponDto;
import com.monkcommerce.couponmanager.entity.Coupon;
import com.monkcommerce.couponmanager.service.CouponService;




@RestController
@RequestMapping("/api/coupons")
public class CouponController {
	
	@Autowired
	CouponService couponService;
	@PostMapping("")
	public Map<String,String> createCoupons(@RequestBody CouponDto entity) {
		//TODO: process POST request
		return couponService.createCoupon(entity);
	}
	
	@GetMapping("")
	public List<Coupon> getAllCoupons() {
		return couponService.getAllCoupons();
	}
	
	@GetMapping("{id}")
	public Coupon getCouponById(@PathVariable Long id) {
		return couponService.getCouponById(id);
	}
	
	
	@DeleteMapping("{id}")
	public Map<String,String> deleteCouponById(@PathVariable Long id) {
		return couponService.deleteCoupon(id);
	}
	
	@PostMapping("/applicable-coupons")
	public List<CouponDto> couponsAvailableForCart(@RequestBody Cart cart) {
		
		return couponService.getApplicableCoupons(cart);
	}
	
	@PostMapping("/apply-coupon")
	public String applyCouponOnCart(@RequestBody String entity) {
		//TODO: process POST request
		
		return entity;
	}
	
	
	
	

}
