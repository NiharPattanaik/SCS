package com.sales.crm.model;

import java.util.List;
import java.util.Set;

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
@Table(name = "USER")
@AttributeOverrides({
	@AttributeOverride(name = "companyID", column = @Column(name = "COMPANY_ID")),
	@AttributeOverride(name = "dateCreated", column = @Column(name = "DATE_CREATED")),
	@AttributeOverride(name = "dateModified", column = @Column(name = "DATE_MODIFIED"))})
public class User extends BusinessEntity{

	private static final long serialVersionUID = 0l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int userID;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "MOBILE_NO")
	private String mobileNo;
	
	@Column(name = "EMAIL_ID")
	private String emailID;
	
	@Column(name = "STATUS")
	private int status;
	
	@Column(name= "LOGGED_IN")
	private int loggedIn;
	
	@Transient
	private List<Role> roles;
	
	@Transient
	private Set<Integer> roleIDs;
	
	@Transient
	private int resellerID;
	
	@Transient
	private String name;
	
	@Transient
	private String newPassword;
	
	@Transient
	private int passwordMedium;
	
	@Transient
	private List<SecurityQuestion> securityQuestions;
	
	@Transient
	private List<String> secQuestionAnsws;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public int getResellerID() {
		return resellerID;
	}

	public void setResellerID(int resellerID) {
		this.resellerID = resellerID;
	}

	public Set<Integer> getRoleIDs() {
		return roleIDs;
	}

	public void setRoleIDs(Set<Integer> roleIDs) {
		this.roleIDs = roleIDs;
	}

	public String getName() {
		return firstName +" "+lastName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public int getPasswordMedium() {
		return passwordMedium;
	}

	public void setPasswordMedium(int passwordMedium) {
		this.passwordMedium = passwordMedium;
	}
	
	public List<SecurityQuestion> getSecurityQuestions() {
		return securityQuestions;
	}

	public void setSecurityQuestions(List<SecurityQuestion> securityQuestions) {
		this.securityQuestions = securityQuestions;
	}
	
	public int getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(int loggedIn) {
		this.loggedIn = loggedIn;
	}

	
	public List<String> getSecQuestionAnsws() {
		return secQuestionAnsws;
	}

	public void setSecQuestionAnsws(List<String> secQuestionAnsws) {
		this.secQuestionAnsws = secQuestionAnsws;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", userName=" + userName + ", password=" + password + ", description="
				+ description + ", firstName=" + firstName + ", lastName=" + lastName + ", mobileNo=" + mobileNo
				+ ", emailID=" + emailID + ", status=" + status + ", roles=" + roles + ", roleIDs=" + roleIDs
				+ ", resellerID=" + resellerID + ", name=" + name + ", newPassword=" + newPassword + "]";
	}
	
	
	
}
