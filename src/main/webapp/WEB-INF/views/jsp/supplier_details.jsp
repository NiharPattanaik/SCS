<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">

<head>
	<title>Supplier Details</title>
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
		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#"></a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href="<%=request.getContextPath()%>/web/customerWeb/list/<%=Integer.valueOf(String.valueOf(session.getAttribute("resellerID")))%>">Customers</a></li>
					<li><a href="<%=request.getContextPath()%>/web/supplierWeb/list/<%=Integer.valueOf(String.valueOf(session.getAttribute("resellerID")))%>">Suppliers</a></li>
					<li><a href="<%=request.getContextPath()%>/web/areaWeb/list/<%=Integer.valueOf(String.valueOf(session.getAttribute("resellerID")))%>">Areas</a></li>
					<li><a href="<%=request.getContextPath()%>/web/beatWeb/list/<%=Integer.valueOf(String.valueOf(session.getAttribute("resellerID")))%>">Beats</a></li>
					<li class="dropdown">
        				<a class="dropdown-toggle" data-toggle="dropdown" href="#">Administration
        				<span class="caret"></span></a>
	      				<ul class="dropdown-menu">
				        	<li><a href="<%=request.getContextPath()%>/web/resellerWeb/<%=Integer.valueOf(String.valueOf(session.getAttribute("resellerID")))%>">Profile</a></li>
				          	<li><a href="<%=request.getContextPath()%>/web/userWeb/list/<%=Integer.valueOf(String.valueOf(session.getAttribute("resellerID")))%>">Users</a></li>
							<li><a href="<%=request.getContextPath()%>/web/role/list">Roles</a></li>
							<li><a href="<%=request.getContextPath()%>/web/salesExecWeb/beatlist/<%=Integer.valueOf(String.valueOf(session.getAttribute("resellerID")))%>">Sales Executive-Beats</a></li>
				          	<li><a href="<%=request.getContextPath()%>/web/beatWeb/beat-customers/list">Beat - Customer</a></li>
				          	<li><a href="<%=request.getContextPath()%>/web/salesExecWeb/salesExecBeatsCustList">Scheduled Visit</a></li>
	      				</ul>
      				</li>
				</ul>	
				<ul class="nav navbar-nav navbar-right ">	
					<li><a href="<%=request.getContextPath()%>/logout">logout</a></li>
				</ul>
			</div>
		</nav>
		<div class="row customer_list">
			<div class="col-md-4">
				<h2>Suppliers Details</h2>
			</div>
			<div class="col-md-4 add_customer">
				<button type="submit" class="btn btn-primary"
					onclick="location.href='<%=request.getContextPath()%>/web/supplierWeb/editSupplierForm/${supplier.supplierID}';">
					Modify Supplier</button>
				<button type="submit" class="btn btn-primary"
					onclick="location.href='<%=request.getContextPath()%>/web/supplierWeb/delete/${supplier.supplierID}';">
					Delete Supplier</button>
			</div>
		</div>
		<div class="row top-height">
			<div class="col-md-8 ">

				<fieldset>
					<legend>Supplier Details</legend>
					<div>
						<label>Name : </label> <span>${supplier.name}</span>
					</div>
					<div class="form-group">
						<label>Description :</label> <span>${supplier.description}</span>
					</div>
				</fieldset>

				<fieldset>
					<legend>Main Address</legend>
					<div class="form-group">
						<label>Contact Person :</label> <span>${supplier.address[0].contactPerson}</span>
					</div>
					<div class="form-group">
						<label>Address Line 1 :</label> <span>${supplier.address[0].addressLine1}</span>
					</div>
					<div class="form-group">
						<label>Address Line 2 :</label> <span>${supplier.address[0].addressLine2}</span>
					</div>
					<div class="form-group">
						<label>Street :</label> <span>${supplier.address[0].street}</span>
					</div>
					<div class="form-group">
						<label>City :</label> <span>${supplier.address[0].city}</span>
					</div>
					<div class="form-group">
						<label>State :</label> <span>${supplier.address[0].state}</span>
					</div>
					<div class="form-group">
						<label>Country : </label> <span>${supplier.address[0].country}</span>
					</div>
					<div class="form-group">
						<label>Postal Code :</label> <span>${supplier.address[0].postalCode}</span>
					</div>
					<div class="form-group">
						<label>Phone Number :</label> <span>${supplier.address[0].phoneNumber}</span>
					</div>
					<div class="form-group">
						<label>Mobile Number(Primary) :</label> <span>${supplier.address[0].mobileNumberPrimary}</span>
					</div>
					<div class="form-group">
						<label>Mobile Number(Secondary) :</label> <span>${supplier.address[0].mobileNumberSecondary}</span>
					</div>
				</fieldset>
				<c:if test="${not empty supplier.address[1]}">
					<fieldset>
						<legend>Billing Address</legend>
						<div class="form-group">
							<label>Contact Person :</label> <span>${supplier.address[1].contactPerson}</span>
						</div>
						<div class="form-group">
							<label>Address Line 1 :</label> <span>${supplier.address[1].addressLine1}</span>
						</div>
						<div class="form-group">
							<label>Address Line 2 :</label> <span>${supplier.address[1].addressLine2}</span>
						</div>
						<div class="form-group">
							<label>Street :</label> <span>${supplier.address[1].street}</span>
						</div>
						<div class="form-group">
							<label>City :</label> <span>${supplier.address[1].city}</span>
						</div>
						<div class="form-group">
							<label>State :</label> <span>${supplier.address[1].state}</span>
						</div>
						<div class="form-group">
							<label>Country : </label> <span>${supplier.address[1].country}</span>
						</div>
						<div class="form-group">
							<label>Postal Code :</label> <span>${supplier.address[1].postalCode}</span>
						</div>
						<div class="form-group">
							<label>Phone Number :</label> <span>${supplier.address[1].phoneNumber}</span>
						</div>
						<div class="form-group">
							<label>Mobile Number(Primary) :</label> <span>${supplier.address[1].mobileNumberPrimary}</span>
						</div>
						<div class="form-group">
							<label>Mobile Number(Secondary) :</label> <span>${supplier.address[1].mobileNumberSecondary}</span>
						</div>
					</fieldset>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
