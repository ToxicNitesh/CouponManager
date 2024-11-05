package com.monkcommerce.couponmanager.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.monkcommerce.couponmanager.dto.ProductDto;
import com.monkcommerce.couponmanager.entity.CouponType;

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

	public Map<Long, String> convertListToMap(List<CouponType> typeList) {
		return typeList.stream().collect(Collectors.toMap(CouponType::getId, CouponType::getCouponType));
	}
}
