package com.monkcommerce.couponmanager.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	private Long id;
	private List<ProductDto> products;
	private Long custId;
	private String paymentType;
	private Double shippingPrice;
	private Double total;
	private Double disc;
	private Double finalPrice;
}
