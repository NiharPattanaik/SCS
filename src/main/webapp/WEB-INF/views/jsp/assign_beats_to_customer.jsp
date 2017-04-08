<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.sales.crm.model.Beat"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
	<title>Assign Beats To Customer</title>
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
				<form:form modelAttribute="beat" method="post"
					action="/crm/web/beatWeb/assignBeatToCustomers">
					<fieldset>
						<legend>Assign Beats to Customer</legend>
						<div class="form-group">
								<label>Beat</label>
								<form:select path="beatID" cssClass="form-control">
									<form:option value="-1" label="--- Select ---" />
									<c:forEach var="cbeat" items="${beats}">
										<% 
											if(!((Beat)pageContext.getAttribute("cbeat") != null 
												&& ((Beat)pageContext.getAttribute("cbeat")).getCustomers() != null 
													&& ((Beat)pageContext.getAttribute("cbeat")).getCustomers().size() > 0)){
										%>
											<form:option value="${ cbeat.beatID }" label="${ cbeat.name }" />
										<% 
											}
										%>
											
									</c:forEach>
								</form:select>
							</div>
							<div class="form-group">
								<label>Customers</label>
								<form:select path="customerIDs" cssClass="form-control" multiple="true">
									<form:option value="-1" label="--- Select ---" />
									<form:options items="${customers}" itemValue="customerID"
										itemLabel="customerName" />
								</form:select>
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
</html>
