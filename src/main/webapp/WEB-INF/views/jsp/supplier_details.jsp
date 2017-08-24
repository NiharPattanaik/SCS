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
		<%@ include file="menus.jsp" %>
		<div class="row customer_list">
			<div class="col-md-4">
				<h2>Suppliers Details</h2>
			</div>
			<div class="col-md-4 add_customer">
				<% if(resourcePermIDs.contains(ResourcePermissionEnum.SUPPLIER_UPDATE.getResourcePermissionID())) { %>
					<button type="submit" class="btn btn-primary"
						onclick="location.href='<%=request.getContextPath()%>/web/supplierWeb/editSupplierForm/${supplier.supplierID}';">
						Modify Supplier</button>
				<% } %>
				
				<% if(resourcePermIDs.contains(ResourcePermissionEnum.SUPPLIER_DELETE.getResourcePermissionID())) { %>
					<button type="submit" class="btn btn-primary" id="deleteBtn" data-toggle="modal" data-target="#confirm">
						Delete Supplier</button>
				<% } %>		
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
					<legend>Sales Officer</legend>
						<div class="form-group">
							<label>Name : </label> <span>${supplier.salesOfficer.name}</span>
						</div>
						<div class="form-group">
							<label>Effective From :</label> <span>${supplier.salesOfficer.effectiveFromStr}</span>
						</div>
						<div class="form-group">
							<label>Contact Number :</label> <span>${supplier.salesOfficer.contactNo}</span>
						</div>
				</fieldset>
				
				<fieldset>
					<legend>Area Manager</legend>
						<div class="form-group">
							<label>Name : </label> <span>${supplier.areaManager.name}</span>
						</div>
						<div class="form-group">
							<label>Effective From :</label> <span>${supplier.areaManager.effectiveFromStr}</span>
						</div>
						<div class="form-group">
							<label>Contact Number :</label> <span>${supplier.areaManager.contactNo}</span>
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
				<fieldset>
					<legend>Beats</legend>
					<div
						style="width: 200px; min-height: 2px; max-height: 100px; overflow-y: auto;"
						id="checks">
						<ul>
							<c:forEach var="beat" items="${supplier.beats}">
								<li><a href="<%=request.getContextPath()%>/web/beatWeb/${beat.beatID}">${beat.name}</a></li>
							</c:forEach>
						</ul>

					</div>
				</fieldset>
			</div>
		</div>
	</div>
	<div class="modal fade" id="confirm" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<b>Confirm removal of supplier.</b>
				</div>
				<div class="modal-body">
					Are you sure you want to remove the supplier, <span><b>${supplier.name}</b></span>
					?
				</div>
				<div class="modal-custom-footer">
					<button type="submit" id="modalSubmit" class="btn btn-primary">Confirm</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
					<script type="text/javascript">
						$('#modalSubmit').click(function(){
						   window.location.href = "/crm/web/supplierWeb/delete/${supplier.supplierID}"
						});
					</script>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
