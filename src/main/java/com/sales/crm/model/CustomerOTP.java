package com.sales.crm.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CUSTOMER_OTP")
@AttributeOverrides({
	@AttributeOverride(name = "companyID", column = @Column(name = "COMPANY_ID")),
	@AttributeOverride(name = "dateCreated", column = @Column(name = "DATE_CREATED")),
	@AttributeOverride(name = "dateModified", column = @Column(name = "DATE_MODIFIED"))})

public class CustomerOTP {
	
	@Transient
	public static final int OTP_ORDER_BOOKING = 1;
	@Transient
	public static final int OTP_DELIVERY_CONF = 2;
	@Transient
	public static final int OTP_PAYMENT_CONF = 3;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int otpID;
	
	@Column(name = "CUSTOMER_ID")
	private int customerID;
	
	@Column(name = "RESELLER_ID")
	private int resellerID;
	
	@Column(name = "SALES_EXEC_ID")
	private int salesExecID;
	
	@Column(name = "GENERTAED_OTP")
	private String genaratedOTP;
	
	@Column(name = "SUBMITTED_OTP")
	private String submiitedOTP;
	
	@Column(name = "OTP_TYPE")
	private int otpType;
	
	@Column(name = "GENERATED_DATE_TIME")
	private Date otpGeneratedDateTime;
	
	@Column(name = "SUBMITTED_DATE_TIME")
	private Date otpSubmitedDateTime;
	
	@Column(name = "RETRY_COUNT")
	private int retryCount;

	public int getOtpID() {
		return otpID;
	}

	public void setOtpID(int otpID) {
		this.otpID = otpID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getResellerID() {
		return resellerID;
	}

	public void setResellerID(int resellerID) {
		this.resellerID = resellerID;
	}

	public int getSalesExecID() {
		return salesExecID;
	}

	public void setSalesExecID(int salesExecID) {
		this.salesExecID = salesExecID;
	}

	public String getGenaratedOTP() {
		return genaratedOTP;
	}

	public void setGenaratedOTP(String genaratedOTP) {
		this.genaratedOTP = genaratedOTP;
	}

	public String getSubmiitedOTP() {
		return submiitedOTP;
	}

	public void setSubmiitedOTP(String submiitedOTP) {
		this.submiitedOTP = submiitedOTP;
	}

	public int getOtpType() {
		return otpType;
	}

	public void setOtpType(int otpType) {
		this.otpType = otpType;
	}

	public Date getOtpGeneratedDateTime() {
		return otpGeneratedDateTime;
	}

	public void setOtpGeneratedDateTime(Date otpGeneratedDateTime) {
		this.otpGeneratedDateTime = otpGeneratedDateTime;
	}

	public Date getOtpSubmitedDateTime() {
		return otpSubmitedDateTime;
	}

	public void setOtpSubmitedDateTime(Date otpSubmitedDateTime) {
		this.otpSubmitedDateTime = otpSubmitedDateTime;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	
	

}