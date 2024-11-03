package com.monkcommerce.couponmanager.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.monkcommerce.couponmanager.dto.ProductDto;

@Component
public class CouponUtil {

	public Double addQuantity(List<ProductDto>getList,int multiply) {
		Double discount=0.0;
		for(ProductDto dto:getList) {
			dto.setQuantity(dto.getQuantity()*multiply);
			discount+=dto.getPrice();
		}
		return discount;
	}
}
