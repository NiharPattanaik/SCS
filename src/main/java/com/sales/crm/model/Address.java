package com.sales.crm.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS")
@AttributeOverrides({
	@AttributeOverride(name = "companyID", column = @Column(name = "COMPANY_ID")),
	@AttributeOverride(name = "dateCreated", column = @Column(name = "DATE_CREATED")),
	@AttributeOverride(name = "dateModified", column = @Column(name = "DATE_MODIFIED"))})
public class Address extends BusinessEntity{
	
	private static final long serialVersionUID = 0l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	
	@Column(name = "ADDRESS_LINE1")
	private String addressLine1;
	
	@Column(name = "ADDRESS_LINE2")
	private String addressLine2;
	
	@Column(name = "STREET")
	private String street;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "POSTAL_CODE")
	private String postalCode;
	
	@Column(name = "CONTACT_PERSON")
	private String contactPerson;
	
	@Column(name = "PHONE_NO")
	private String phoneNumber;
	
	@Column(name = "MOBILE_NUMBER_PRIMARY")
	private String mobileNumberPrimary;
	
	@Column(name = "MOBILE_NUMBER_SECONDARY")
	private String mobileNumberSecondary;
	
	@Column(name = "ADDRESS_TYPE")
	private int addrressType;
	
	@Column(name = "EMAIL_ID")
	private String emailID;
	
	public String getAddressLine1() {
		return addressLine1;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getAddrressType() {
		return addrressType;
	}
	public void setAddrressType(int addrressType) {
		this.addrressType = addrressType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMobileNumberPrimary() {
		return mobileNumberPrimary;
	}
	public void setMobileNumberPrimary(String mobileNumberPrimary) {
		this.mobileNumberPrimary = mobileNumberPrimary;
	}
	public String getMobileNumberSecondary() {
		return mobileNumberSecondary;
	}
	public void setMobileNumberSecondary(String mobileNumberSecondary) {
		this.mobileNumberSecondary = mobileNumberSecondary;
	}
	
	
}
