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
		<!-- Links -->
		<%@ include file="menus.jsp" %>
		<div class="row customer_list">
			<div class="col-md-4">
				<h2>Area Details</h2>
			</div>
			<div class="col-md-4 add_customer">
				<% if(resourcePermIDs.contains(ResourcePermissionEnum.AREA_UPDATE.getResourcePermissionID())) { %>
					<button type="submit" class="btn btn-primary"
						onclick="location.href='<%=request.getContextPath()%>/web/areaWeb/editAreaForm/${area.areaID}';">
						Modify Area</button>
				<% } %>	
				
				<% if(resourcePermIDs.contains(ResourcePermissionEnum.AREA_DELETE.getResourcePermissionID())) { %>	
					<button type="submit" class="btn btn-primary"
						onclick="location.href='<%=request.getContextPath()%>/web/areaWeb/delete/${area.areaID}';">
						Delete Area</button>
				<% } %>			
			</div>
		</div>
		<div class="row top-height">
			<div class="col-md-8 ">
				<fieldset>
					<legend>Area Details</legend>
					<div class="form-group">
						<label>Area Name : </label> <span>${ area.name }</span>
					</div>
					<div class="form-group">
						<label>Description : </label> <span>${ area.description }</span>
					</div>
					<div class="form-group">
						<label>Word Number : </label> <span>${ area.wordNo }</span>
					</div>
					<div class="form-group">
						<label>PIN Code : </label> <span>${ area.pinCode }</span>
					</div>
				</fieldset>
			</div>
		</div>
	</div>
</body>
</html>
