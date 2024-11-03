package com.monkcommerce.couponmanager.mapper;

import org.mapstruct.Mapper;

import com.monkcommerce.couponmanager.dto.CouponDto;
import com.monkcommerce.couponmanager.entity.Coupon;

@Mapper(componentModel = "spring")
public interface CouponMapper {
	public Coupon couponDtoToCoupon(CouponDto dto);
}
