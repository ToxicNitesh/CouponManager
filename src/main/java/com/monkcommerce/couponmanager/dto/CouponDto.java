package com.monkcommerce.couponmanager.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDto {
	private Long id;
	private String code;
	private String type;
	private Condition conditon;
	private Date from;
	private Date to;
	private Integer usageLimit;
	private Integer perUserLimit;
	private Long logonAdd;
	
	private Double discount;
	public CouponDto(Long id,String type,Double discount) {
		/*
		 * using this constructor to return applicable coupons
		 */
		this.id=id;
		this.type=type;
		this.discount=discount;
	}

}
