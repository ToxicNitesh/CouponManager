package com.monkcommerce.couponmanager.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Condition {
	// condition for cartwise
	private Integer discountType;//amt,percent,freebie
	private Integer discount;
	private Integer discMax;
	private Integer minVal;
	// condition for bxgy
	private Map<String, List<ProductDto>> bxgy;
	// condition for productWise
	private ProductDto productWise;
	// and other type of coupons

}
