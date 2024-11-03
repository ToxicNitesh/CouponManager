package com.monkcommerce.couponmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	/*
	 * using this dto for product based coupons.
	 */
	private Long id;
	private String name;
	private Integer quantity;
	private String brand;
	private Integer discType;
	private Integer discount;
	private Integer discMax;
	private String type;
	private Integer price;

	public ProductDto(Long id, Integer quan, Integer discType, Integer discount) {
		/*
		 * using these parameters to add coupons.
		 */
		this.id = id;
		this.quantity = quan;
		this.discount = discount;
		this.discType = discType;
	}

	public ProductDto(Long id, Integer quan, Integer price) {
		/*
		 * using these parameters to add products in cart.
		 */
		this.id = id;
		this.quantity = quan;
		this.price = price;
	}
}
