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
@Table(name = "PRODUCT")
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "I_ID")
	private Long Id;
	@Column(name="C_PRODUCT_NAME")
	private String productName;
	@Column(name="C_PRODUCT_CATEGORY")
	private String productCategory;
	@Column(name="C_PRODUCT_QTY")
	private Long productQty;
	@Column(name="C_PRODUCT_PRICE")
	private Long productPrice;
	@Column(name = "X_LOGON_ADD")
	private String logonAdd;
	@Column(name = "T_TIMESTAMP_ADD")
	private Timestamp timeStampAdd;
	@Column(name = "X_LOGON_UPD")
	private String logonUpd;
	@Column(name = "T_TIMESTAMP_UPD")
	private Timestamp timeStampUpd;
}
