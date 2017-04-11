<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">

<head>
	<title>Edit Supplier</title>
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

.form_submit {
	margin-top: 14px;
	text-align: right;
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
		<div class="row top-height">
			<div class="col-md-8 ">
				<h2>Edit Supplier</h2>
				<form:form modelAttribute="supplier" method="post"
					action="/crm/web/supplierWeb/update">
					<fieldset>
						<legend>Supplier Details</legend>
						<div class="form-group">
							<label>Name</label>
							<form:input name="name" cssClass="form-control" path="name"
								value="${supplier.name}" />
						</div>
						<div class="form-group">
							<label>Description</label>
							<form:input name="description" cssClass="form-control"
								path="description" value="${supplier.description}" />
						</div>
						<form:hidden name="supplierID" path="supplierID"
							value="${ supplier.supplierID }" />
						<form:hidden name="resellerID" path="resellerID"
							value="${ supplier.resellerID }" />
					</fieldset>

					<fieldset>
						<legend>Supplier Main Address</legend>
						<form:hidden name="addrressType" value="1"
							path="address[0].addrressType" />
						<form:hidden name="id" value="${ supplier.address[0].id }"
							path="address[0].id" />
						<div class="form-group">
							<label>Contact Person</label>
							<form:input name="contactPerson" cssClass="form-control"
								path="address[0].contactPerson"
								value="${supplier.address[0].contactPerson}" />
						</div>
						<div class="form-group">
							<label>Address Line 1</label>
							<form:input name="addressLine1" cssClass="form-control"
								path="address[0].addressLine1"
								value="${ supplier.address[0].addressLine1 }" />
						</div>
						<div class="form-group">
							<label>Address Line 2</label>
							<form:input name="addressLine2" cssClass="form-control"
								path="address[0].addressLine2"
								value="${ supplier.address[0].addressLine2 }" />
						</div>
						<div class="form-group">
							<label>Street</label>
							<form:input name="street" cssClass="form-control"
								path="address[0].street" value="${ supplier.address[0].street }" />
						</div>
						<div class="form-group">
							<label>City</label>
							<form:input name="city" cssClass="form-control"
								path="address[0].city" value="${ supplier.address[0].city }" />
						</div>
						<div class="form-group">
							<label>State</label>
							<form:input name="state" cssClass="form-control"
								path="address[0].state" value="${ supplier.address[0].state }" />
						</div>
						<div class="form-group">
							<label>Country</label>
							<form:input name="country" cssClass="form-control"
								path="address[0].country"
								value="${ supplier.address[0].country }" />
						</div>
						<div class="form-group">
							<label>Postal Code</label>
							<form:input name="postalCode" cssClass="form-control"
								path="address[0].postalCode"
								value="${ supplier.address[0].postalCode }" />
						</div>
						<div class="form-group">
							<label>Phone Number</label>
							<form:input name="phoneNumber" cssClass="form-control"
								path="address[0].phoneNumber"
								value="${ supplier.address[0].phoneNumber }" />
						</div>
						<div class="form-group">
							<label>Mobile Number(Primary)</label>
							<form:input name="mobileNumberPrimary" cssClass="form-control"
								path="address[0].mobileNumberPrimary"
								value="${ supplier.address[0].mobileNumberPrimary }" />
						</div>
						<div class="form-group">
							<label>Mobile Number(Secondary)</label>
							<form:input name="mobileNumberSecondary" cssClass="form-control"
								path="address[0].mobileNumberSecondary"
								value="${ supplier.address[0].mobileNumberSecondary }" />
						</div>
					</fieldset>
					<fieldset>
						<legend>Supplier Billing Address</legend>
						<form:hidden name="addrressType" value="2"
							path="address[1].addrressType" />
						<form:hidden name="id" value="${ supplier.address[1].id }"
							path="address[1].id" />
						<div class="form-group">
							<label>Contact Person</label>
							<form:input name="contactPerson" cssClass="form-control"
								path="address[1].contactPerson"
								value="${ supplier.address[1].contactPerson }" />
						</div>
						<div class="form-group">
							<label>Address Line 1</label>
							<form:input name="addressLine1" cssClass="form-control"
								path="address[1].addressLine1"
								value="${ supplier.address[1].addressLine1 }" />
						</div>
						<div class="form-group">
							<label>Address Line 2</label>
							<form:input name="addressLine2" cssClass="form-control"
								path="address[1].addressLine2"
								value="${ supplier.address[1].addressLine2 }" />
						</div>
						<div class="form-group">
							<label>Street</label>
							<form:input name="street" cssClass="form-control"
								path="address[1].street" value="${ supplier.address[1].street }" />
						</div>
						<div class="form-group">
							<label>City</label>
							<form:input name="city" cssClass="form-control"
								path="address[1].city" value="${ supplier.address[1].city }" />
						</div>
						<div class="form-group">
							<label>State</label>
							<form:input name="state" cssClass="form-control"
								path="address[1].state" value="${ supplier.address[1].state }" />
						</div>
						<div class="form-group">
							<label>Country</label>
							<form:input name="country" cssClass="form-control"
								path="address[1].country"
								value="${ supplier.address[1].country }" />
						</div>
						<div class="form-group">
							<label>Postal Code</label>
							<form:input name="postalCode" cssClass="form-control"
								path="address[1].postalCode"
								value="${ supplier.address[1].postalCode }" />
						</div>
						<div class="form-group">
							<label>Phone Number</label>
							<form:input name="phoneNumber" cssClass="form-control"
								path="address[1].phoneNumber"
								value="${ supplier.address[1].phoneNumber }" />
						</div>
						<div class="form-group">
							<label>Mobile Number(Primary)</label>
							<form:input name="mobileNumberPrimary" cssClass="form-control"
								path="address[1].mobileNumberPrimary"
								value="${ supplier.address[1].mobileNumberPrimary }" />
						</div>
						<div class="form-group">
							<label>Mobile Number(Secondary)</label>
							<form:input name="mobileNumberSecondary" cssClass="form-control"
								path="address[1].mobileNumberSecondary"
								value="${ supplier.address[1].mobileNumberSecondary }" />
						</div>
					</fieldset>
					<div>
						<fmt:formatDate value="${ supplier.dateCreated }" type="date"
								pattern="dd/MM/yyyy" var="createdDate" />
						<form:hidden path="dateCreated" value="${createdDate}"/>
					</div>
					<div>
						<fmt:formatDate value="${ supplier.dateModified }" type="date"
								pattern="dd/MM/yyyy" var="modifiedDate" />
						<form:hidden path="dateModified" value="${modifiedDate}"/>
					</div>
					<div>
						<form:hidden path="companyID" value="${ supplier.companyID }"/>
					</div>
					<div class="form_submit">
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
