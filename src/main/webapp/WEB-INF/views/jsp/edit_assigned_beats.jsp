<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.sales.crm.model.SalesExecutive"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
	<title>Edit Assign Beats</title>
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

.side_nav_btns {
	margin-top: 10px;
}

}
.top-height {
	margin-top: 2%;
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
		<%@ include file="menus.jsp" %>
		<div class="row top-height">
			<div class="col-md-8 ">
				<form:form modelAttribute="manufSalesExecBeats" method="post"
					action="/crm/web/salesExecWeb/updateAssignedBeats">
					<fieldset>
						<legend>Assign Beats to Sales Executive</legend>
							<div class="form-group">
								<label>Manufacturer</label>
								<form:input name="manufacturerName" cssClass="form-control"
								path="manufacturer.name" value="${ manufSalesExecBeats.manufacturer.name }" readonly="true"/>
							</div>
							<div class="form-group">
								<label>Sales Executive</label>
								<form:input name="manufacturerID" cssClass="form-control"
								path="salesExecutive.name" value="${ manufSalesExecBeats.salesExecutive.name }" readonly="true"/>
							</div>
							<div class="form-group">
								<label>Beats</label>
								<form:select path="beatIDLists" cssClass="form-control" multiple="true">
									<form:option value="-1" label="--- Select ---" />
									<form:options items="${beats}" itemValue="beatID"
										itemLabel="name" />
								</form:select>
							</div>
							<form:hidden path="manufacturer.manufacturerID" value="${ manufSalesExecBeats.manufacturer.manufacturerID }"/>
							<form:hidden path="salesExecutive.userID" value="${ manufSalesExecBeats.salesExecutive.userID }"/>
					</fieldset>
					<div class="form_submit">
						<button type="button" class="btn btn-primary" id="cancelbtn" onclick="window.history.back(); return false;"">Cancel</button>
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
