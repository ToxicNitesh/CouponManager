package com.monkcommerce.couponmanager.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUSTOMER")
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "I_ID")
	private long id;
	@Column(name = "C_FIRST_NAME")
	private String firstName;
	@Column(name = "C_LAST_NAME")
	private String lastName;
	@Column(name = "C_EMAIL")
	private String email;
	@Column(name = "X_CONTACT")
	private String contact;
	@ManyToOne
	@JoinColumn(name = "I_ROLE_ID")
	private Role role;
	@Column(name = "C_FIRST_TIME_USER")
	private String firstTimeUser;
	@Column(name = "C_TRIAL")
	private String trial;
	@Column(name = "X_COUNTRY")
	private String country;
	@Column(name = "X_CITY")
	private String city;
	@Column(name = "X_LOGON_ADD")
	private String logonAdd;
	@Column(name = "T_TIMESTAMP_ADD")
	private Timestamp timeStampAdd;
	@Column(name = "X_LOGON_UPD")
	private String logonUpd;
	@Column(name = "T_TIMESTAMP_UPD")
	private Timestamp timeStampUpd;

	public Customer(long id) {
		this.id = id;
	}

}
