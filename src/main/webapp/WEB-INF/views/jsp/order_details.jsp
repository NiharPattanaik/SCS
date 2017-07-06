<%@page import="com.sales.crm.model.Area"%>
<%@page import="com.sales.crm.model.Role"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.sales.crm.model.Role"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="en">

<head>
<title>Area Details</title>
<!-- Bootstrap Core CSS -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"
	rel="stylesheet" />
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-3.2.0.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>

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

.modal-custom-footer {
	padding: 15px;
	text-align: center;
	border-top: 1px solid #e5e5e5;
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
		<!-- Links -->
		<%@ include file="menus.jsp"%>
		<div class="row customer_list">
			<div class="col-md-4">
				<h2>Order Details</h2>
			</div>
			<div class="col-md-4 add_customer">
				<button type="submit" class="btn btn-primary"
					onclick="location.href='<%=request.getContextPath()%>/web/orderWeb/editOrderForm/${order.orderID}';">
					Modify Order</button>
			</div>
		</div>
		<div class="row top-height">
			<div class="col-md-8 ">
				<fieldset>
					<legend>Order Details</legend>
					<div class="form-group">
						<label>Order ID : </label> <span>${ order.orderID }</span>
					</div>
					<div class="form-group">
						<label>Order Schedule ID : </label> <span>${ order.orderBookingID }</span>
					</div>
					<div class="form-group">
						<label>Customer Name : </label> <span>${ order.customerName }</span>
					</div>
					<div class="form-group">
						<label>No. Of Line Items : </label> <span>${ order.noOfLineItems }</span>
					</div>
					<div class="form-group">
						<label>Aproximate Order Value : </label> <span>${ order.bookValue }</span>
					</div>
					<div class="form-group">
						<label>Remarks : </label> <span>${ order.remark }</span>
					</div>
					<div class="form-group">
						<label>Order Status : </label> <span>${ order.statusAsString }</span>
					</div>
					<div class="form-group">
						<label>Order Date : </label> <span>${ order.dateCreatedString }</span>
					</div>
				</fieldset>
			</div>
		</div>
	</div>
</body>

</html>
