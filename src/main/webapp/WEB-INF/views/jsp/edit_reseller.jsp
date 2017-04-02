<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">

<head>
<title>Page one</title>
<!-- Bootstrap Core CSS -->
<link href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet">
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

.form_submit{
margin-top:14px;
text-align:right;
}
</style>
</head>

<body>
	<!-- Header -->
	<header class="dpHeaderWrap">
        <div class="text-center">
            Header part
        </div>
    </header>
	<!-- Header -->
	<div class="container">
		<!-- Links -->
		<div class="row pull-right side_nav_btns">
			<a href="<%=request.getContextPath()%>/resellerWeb/13">Profile</a> 
			<a href="<%=request.getContextPath()%>/customerWeb/list/13">Customers</a>
			<a href="<%=request.getContextPath()%>/userWeb/list/13">Users</a>
			<a href="<%=request.getContextPath()%>/role/list">Roles</a>
			<a href="<%=request.getContextPath()%>/areaWeb/list/13">Areas</a>
			<a href="<%=request.getContextPath()%>/beatWeb/list/13">Beats</a> 
		</div>
		<div class="row top-height">
			<div class="col-md-8 ">
				<h2>Edit Reseller</h2>
				<form:form modelAttribute="reseller" method="post"
					action="/crm/resellerWeb/update">
					<fieldset>
						<legend>Reseller Details</legend>
						<div class="form-group">
							<label>Name</label>
							<form:input name="name" cssClass="form-control" path="name" value="${reseller.name}"/>
						</div>
						<div class="form-group">
							<label>Description</label>
							<form:input name="description" cssClass="form-control"
								path="description" value = "${reseller.description}"/>
						</div>
						<form:hidden name="resellerID" path="resellerID" value="${ reseller.resellerID }"/>
						<form:hidden name="resellerID" path="resellerID" value="${ reseller.resellerID }"/>
					</fieldset>

					<fieldset>
						<legend>Reseller Main Address</legend>
						<form:hidden name="addrressType" value="1"
							path="address[0].addrressType" />
						<form:hidden name="id" value="${ reseller.address[0].id }"
							path="address[0].id" />	
						<div class="form-group">
							<label>Contact Person</label>
							<form:input name="contactPerson" cssClass="form-control"
								path="address[0].contactPerson" value="${reseller.address[0].contactPerson}" />
						</div>
						<div class="form-group">
							<label>Address Line 1</label>
							<form:input name="addressLine1" cssClass="form-control"
								path="address[0].addressLine1" value="${ reseller.address[0].addressLine1 }"/>
						</div>
						<div class="form-group">
							<label>Address Line 2</label>
							<form:input name="addressLine2" cssClass="form-control"
								path="address[0].addressLine2" value="${ reseller.address[0].addressLine2 }"/>
						</div>
						<div class="form-group">
							<label>Street</label>
							<form:input name="street" cssClass="form-control"
								path="address[0].street" value="${ reseller.address[0].street }"/>
						</div>
						<div class="form-group">
							<label>City</label>
							<form:input name="city" cssClass="form-control"
								path="address[0].city" value="${ reseller.address[0].city }"/>
						</div>
						<div class="form-group">
							<label>State</label>
							<form:input name="state" cssClass="form-control"
								path="address[0].state" value="${ reseller.address[0].state }"/>
						</div>
						<div class="form-group">
							<label>Country</label>
							<form:input name="country" cssClass="form-control"
								path="address[0].country" value="${ reseller.address[0].country }"/>
						</div>
						<div class="form-group">
							<label>Postal Code</label>
							<form:input name="postalCode" cssClass="form-control"
								path="address[0].postalCode" value ="${ reseller.address[0].postalCode }"/>
						</div>
						<div class="form-group">
							<label>Phone Number</label>
							<form:input name="phoneNumber" cssClass="form-control"
								path="address[0].phoneNumber" value="${ reseller.address[0].phoneNumber }"/>
						</div>
						<div class="form-group">
							<label>Mobile Number(Primary)</label>
							<form:input name="mobileNumberPrimary" cssClass="form-control"
								path="address[0].mobileNumberPrimary" value="${ reseller.address[0].mobileNumberPrimary }"/>
						</div>
						<div class="form-group">
							<label>Mobile Number(Secondary)</label>
							<form:input name="mobileNumberSecondary" cssClass="form-control"
								path="address[0].mobileNumberSecondary" value="${ reseller.address[0].mobileNumberSecondary }"/>
						</div>
					</fieldset>
					<fieldset>
						<legend>Reseller Billing Address</legend>
						<form:hidden name="addrressType" value="2"
							path="address[1].addrressType" />
						<form:hidden name="id" value="${ reseller.address[1].id }"
							path="address[1].id" />	
						<div class="form-group">
							<label>Contact Person</label>
							<form:input name="contactPerson" cssClass="form-control"
								path="address[1].contactPerson" value="${ reseller.address[1].contactPerson }"/>
						</div>
						<div class="form-group">
							<label>Address Line 1</label>
							<form:input name="addressLine1" cssClass="form-control"
								path="address[1].addressLine1" value="${ reseller.address[1].addressLine1 }"/>
						</div>
						<div class="form-group">
							<label>Address Line 2</label>
							<form:input name="addressLine2" cssClass="form-control"
								path="address[1].addressLine2" value="${ reseller.address[1].addressLine2 }"/>
						</div>
						<div class="form-group">
							<label>Street</label>
							<form:input name="street" cssClass="form-control"
								path="address[1].street" value="${ reseller.address[1].street }"/>
						</div>
						<div class="form-group">
							<label>City</label>
							<form:input name="city" cssClass="form-control"
								path="address[1].city" value="${ reseller.address[1].city }"/>
						</div>
						<div class="form-group">
							<label>State</label>
							<form:input name="state" cssClass="form-control"
								path="address[1].state" value="${ reseller.address[1].state }"/>
						</div>
						<div class="form-group">
							<label>Country</label>
							<form:input name="country" cssClass="form-control"
								path="address[1].country" value= "${ reseller.address[1].country }"/>
						</div>
						<div class="form-group">
							<label>Postal Code</label>
							<form:input name="postalCode" cssClass="form-control"
								path="address[1].postalCode" value="${ reseller.address[1].postalCode }"/>
						</div>
						<div class="form-group">
							<label>Phone Number</label>
							<form:input name="phoneNumber" cssClass="form-control"
								path="address[1].phoneNumber" value="${ reseller.address[1].phoneNumber }"/>
						</div>
						<div class="form-group">
							<label>Mobile Number(Primary)</label>
							<form:input name="mobileNumberPrimary" cssClass="form-control"
								path="address[1].mobileNumberPrimary" value="${ reseller.address[1].mobileNumberPrimary }"/>
						</div>
						<div class="form-group">
							<label>Mobile Number(Secondary)</label>
							<form:input name="mobileNumberSecondary" cssClass="form-control"
								path="address[1].mobileNumberSecondary" value="${ reseller.address[1].mobileNumberSecondary }"/>
						</div>
					</fieldset>
					<div class="form_submit">
					<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$('#dp').datepicker({format: 'dd/mm/yyyy'});
</script>

</html>
