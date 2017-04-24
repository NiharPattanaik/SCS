<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">

<head>
	<title>Customer Details</title>
	<!-- Bootstrap Core CSS -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet" />
	<script src="<%=request.getContextPath()%>/resources/js/jquery-3.2.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>

<style>
.dpHeaderWrap {
	position: relative;
	width: auto;
	height: 80px;
	background: #fff;
	border-style: solid;
	border-bottom-style: groove;
	border-top-style: none;
	border-left-style: none;
	border-right-style: none;
	margin: 10px;
}

fieldset {
	border: 1px solid grey;
	padding: 10px;
	border-radius: 5px;
}

legend {
	width: auto !important;
	border-bottom: 0px !important;
}

}
.top-height {
	margin-top: 2%;
}

.customer_list {
	margin-bottom: 20px;
}

.add_customer {
	text-align: right;
	margin-top: 31px;
}

.side_nav_btns {
	margin-top: 10px;
}

.side_nav_btns a {
	text-decoration: none;
	background: #337ab7;
	padding: 11px;
	border-radius: 12px;
	color: #ffffff;
	margin-top: 12px;
}
</style>
</head>

<body>
	<!-- Header -->
	<header class="dpHeaderWrap">
		<div class="text-center">Header part</div>
	</header>
	<!-- Header -->
	<div class="container">
		<%@ include file="menus.jsp" %>
		<div class="row customer_list">
			<div class="col-md-4">
				<h2>Customers Details</h2>
			</div>
			<div class="col-md-4 add_customer">
				<button type="submit" class="btn btn-primary"
					onclick="location.href='<%=request.getContextPath()%>/web/customerWeb/editCustomerForm/${customer.customerID}';">
					Modify Customer</button>
				<button type="submit" class="btn btn-primary"
					onclick="location.href='<%=request.getContextPath()%>/web/customerWeb/delete/${customer.customerID}';">
					Delete Customer</button>
			</div>
		</div>
		<div class="row top-height">
			<div class="col-md-8 ">

				<fieldset>
					<legend>Customer Details</legend>
					<div>
						<label>Name : </label> <span>${customer.name}</span>
					</div>
					<div class="form-group">
						<label>Description :</label> <span>${customer.description}</span>
					</div>
				</fieldset>

				<fieldset>
					<legend>Main Address</legend>
					<div class="form-group">
						<label>Contact Person :</label> <span>${customer.address[0].contactPerson}</span>
					</div>
					<div class="form-group">
						<label>Address Line 1 :</label> <span>${customer.address[0].addressLine1}</span>
					</div>
					<div class="form-group">
						<label>Address Line 2 :</label> <span>${customer.address[0].addressLine2}</span>
					</div>
					<div class="form-group">
						<label>Street :</label> <span>${customer.address[0].street}</span>
					</div>
					<div class="form-group">
						<label>City :</label> <span>${customer.address[0].city}</span>
					</div>
					<div class="form-group">
						<label>State :</label> <span>${customer.address[0].state}</span>
					</div>
					<div class="form-group">
						<label>Country : </label> <span>${customer.address[0].country}</span>
					</div>
					<div class="form-group">
						<label>Postal Code :</label> <span>${customer.address[0].postalCode}</span>
					</div>
					<div class="form-group">
						<label>Phone Number :</label> <span>${customer.address[0].phoneNumber}</span>
					</div>
					<div class="form-group">
						<label>Mobile Number(Primary) :</label> <span>${customer.address[0].mobileNumberPrimary}</span>
					</div>
					<div class="form-group">
						<label>Mobile Number(Secondary) :</label> <span>${customer.address[0].mobileNumberSecondary}</span>
					</div>
				</fieldset>
				<c:if test="${not empty customer.address[1]}">
					<fieldset>
						<legend>Billing Address</legend>
						<div class="form-group">
							<label>Contact Person :</label> <span>${customer.address[1].contactPerson}</span>
						</div>
						<div class="form-group">
							<label>Address Line 1 :</label> <span>${customer.address[1].addressLine1}</span>
						</div>
						<div class="form-group">
							<label>Address Line 2 :</label> <span>${customer.address[1].addressLine2}</span>
						</div>
						<div class="form-group">
							<label>Street :</label> <span>${customer.address[1].street}</span>
						</div>
						<div class="form-group">
							<label>City :</label> <span>${customer.address[1].city}</span>
						</div>
						<div class="form-group">
							<label>State :</label> <span>${customer.address[1].state}</span>
						</div>
						<div class="form-group">
							<label>Country : </label> <span>${customer.address[1].country}</span>
						</div>
						<div class="form-group">
							<label>Postal Code :</label> <span>${customer.address[1].postalCode}</span>
						</div>
						<div class="form-group">
							<label>Phone Number :</label> <span>${customer.address[1].phoneNumber}</span>
						</div>
						<div class="form-group">
							<label>Mobile Number(Primary) :</label> <span>${customer.address[1].mobileNumberPrimary}</span>
						</div>
						<div class="form-group">
							<label>Mobile Number(Secondary) :</label> <span>${customer.address[1].mobileNumberSecondary}</span>
						</div>
					</fieldset>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
