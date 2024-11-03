package com.monkcommerce.couponmanager.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COUPON")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "I_ID")
	private Long couponId;

	@Column(name = "C_COUPON_CODE")
	private String couponCode;

	@Column(name = "C_COUPON_TYPE")
	private Long couponType;
	

	@Column(name = "D_STRT_DATE")
	private Date strtDate;
	
	@Column(name = "D_EXP_DATE")
	private Date expDate;
	
	@Column(name = "JSON_COND",columnDefinition = "JSON")
	private  String condition;

	@Column(name = "L_ACT")
	private String active;

	@Column(name = "I_LOGON_ADD")
	private Long logonAdd;
	
	@Column(name = "T_TIMESTAMP_ADD")
	private Timestamp timeStampAdd;
	
	@Column(name = "I_LOGON_UPD")
	private Long logonUpd;
	
	@Column(name = "T_TIMESTAMP_UPD")
	private Timestamp timeStampUpd;
}
