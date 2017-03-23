<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
<title>Page one</title>
<!-- Bootstrap Core CSS -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet">
<style>
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
.dpHeaderWrap {
	position: relative;
	width: auto;
	height: 50px;
	background: #fff;
	border-style: solid;
	border-bottom-style: groove;
	border-top-style: none;
	border-left-style: none;
	border-right-style: none;
	margin: 10px;
}

.top-height {
	margin-top: 2%;
}
</style>
</head>

<body>
	<!-- Header -->
	<header class="dpHeaderWrap">
		<div class="text-center"></div>
	</header>
	<!-- Header -->
	<div class="container">
		<!-- Links -->
		<div class="row pull-right">
			<a href="">Link 1</a> <a href="">Link 2</a> <a href="">Link 3</a>
			<!-- Links -->
		</div>
		<div class="row top-height">
			<div class="col-md-8 ">
				<h2>Sample form</h2>
				<form:form  modelAttribute="customer" method="post" action="/crm/customerWeb/save">
					<fieldset>
						<legend>Customer Details</legend>
						<div class="form-group">
							<label>Name</label> 
                            <form:input name="name" cssClass="form-control" path="name"/>
						</div>
						<div class="form-group">
							<label>Description</label> 
							<form:input name="description" cssClass="form-control" path="description"/>
						</div>
						<div class="form-group">
							<label>Visit Date</label> 
							<form:input name="visitDate" cssClass="form-control" path="visitDate"/>
						</div>
					</fieldset>
					
					<fieldset>
						<legend>Customer Main Address</legend>
						<form:hidden name="addrressType" value="1" path="address[0].addrressType"/>
						<div class="form-group">
							<label>Contact Person</label> 
							<form:input name="contactPerson" cssClass="form-control" path="address[0].contactPerson"/>
						</div>
						<div class="form-group">
							<label>Address Line 1</label> 
							<form:input name="addressLine1" cssClass="form-control" path="address[0].addressLine1"/>
						</div>
						<div class="form-group">
							<label>Address Line 2</label> 
							<form:input name="addressLine2" cssClass="form-control" path="address[0].addressLine2"/>
						</div>
						<div class="form-group">
							<label>Street</label> 
							<form:input name="street" cssClass="form-control" path="address[0].street"/>
						</div>
						<div class="form-group">
							<label>City</label> 
							<form:input name="city" cssClass="form-control" path="address[0].city"/>
						</div>
						<div class="form-group">
							<label>State</label> 
							<form:input name="state" cssClass="form-control" path="address[0].state"/>
						</div>
						<div class="form-group">
							<label>Country</label> 
							<form:input name="country" cssClass="form-control" path="address[0].country"/>
						</div>
						<div class="form-group">
							<label>Postal Code</label> 
							<form:input name="postalCode" cssClass="form-control" path="address[0].postalCode"/>
						</div>
						<div class="form-group">
							<label>Phone Number</label> 
							<form:input name="phoneNumber" cssClass="form-control" path="address[0].phoneNumber"/>
						</div>
						<div class="form-group">
							<label>Mobile Number(Primary)</label> 
							<form:input name="mobileNumberPrimary" cssClass="form-control" path="address[0].mobileNumberPrimary"/>
						</div>
						<div class="form-group">
							<label>Mobile Number(Secondary)</label>
							<form:input	name="mobileNumberSecondary" cssClass="form-control" path="address[0].mobileNumberSecondary"/>
						</div>
					</fieldset>
					<fieldset>
						<legend>Customer Billing Address</legend>
						<form:hidden name="addrressType" value="2" path="address[1].addrressType"/>
						<div class="form-group">
							<label>Contact Person</label> 
							<form:input name="contactPerson" cssClass="form-control" path="address[1].contactPerson"/>
						</div>
						<div class="form-group">
							<label>Address Line 1</label> 
							<form:input name="addressLine1" cssClass="form-control" path="address[1].addressLine1"/>
						</div>
						<div class="form-group">
							<label>Address Line 2</label> 
							<form:input name="addressLine2" cssClass="form-control" path="address[1].addressLine2"/>
						</div>
						<div class="form-group">
							<label>Street</label> 
							<form:input name="street" cssClass="form-control" path="address[1].street"/>
						</div>
						<div class="form-group">
							<label>City</label> 
							<form:input name="city" cssClass="form-control" path="address[1].city"/>
						</div>
						<div class="form-group">
							<label>State</label> 
							<form:input name="state" cssClass="form-control" path="address[1].state"/>
						</div>
						<div class="form-group">
							<label>Country</label> 
							<form:input name="country" cssClass="form-control" path="address[1].country"/>
						</div>
						<div class="form-group">
							<label>Postal Code</label> 
							<form:input name="postalCode" cssClass="form-control" path="address[1].postalCode"/>
						</div>
						<div class="form-group">
							<label>Phone Number</label> 
							<form:input name="phoneNumber" cssClass="form-control" path="address[1].phoneNumber"/>
						</div>
						<div class="form-group">
							<label>Mobile Number(Primary)</label> 
							<form:input name="mobileNumberPrimary" cssClass="form-control" path="address[1].mobileNumberPrimary"/>
						</div>
						<div class="form-group">
							<label>Mobile Number(Secondary)</label>
							<form:input	name="mobileNumberSecondary" cssClass="form-control" path="address[1].mobileNumberSecondary"/>
						</div>
					</fieldset>
					 
					<button type="submit" class="btn btn-primary">Submit</button>
				</form:form>
			</div>
		</div>
	</div>
</body>

</html>
