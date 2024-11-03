package com.monkcommerce.couponmanager.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COUPON_TYPE")
@Entity
public class CouponType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "I_ID")
	private Long Id;
	@Column(name="C_COUPON_TYPE")
	private String couponType;
	
	public CouponType(String type){
		this.couponType=type;
	}
}
