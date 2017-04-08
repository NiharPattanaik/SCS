<%@page import="com.sales.crm.model.Role"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.sales.crm.model.Area"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="en">

<head>
	<title>Beat Details</title>
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
				<h2>Beat Details</h2>
			</div>
			<div class="col-md-4 add_customer">
				<button type="submit" class="btn btn-primary"
					onclick="location.href='<%=request.getContextPath()%>/web/beatWeb/editBeatForm/${beat.beatID}';">
					Modify Beat</button>
					
				<button type="submit" class="btn btn-primary"
					onclick="location.href='<%=request.getContextPath()%>/web/beatWeb/delete/${beat.beatID}';">
					Delete Beat</button>		
			</div>
		</div>
		<div class="row top-height">
			<div class="col-md-8 ">
				<fieldset>
					<legend>Beat Details</legend>
					<div class="form-group">
						<label>Beat Name : </label> <span>${ beat.name }</span>
					</div>
					<div class="form-group">
						<label>Description : </label> <span>${ beat.description }</span>
					</div>
					<div class="form-group">
						<label>Coverage Schedule : </label> <span>${ beat.coverageSchedule }</span>
					</div>
					<div class="form-group">
						<label>Distance : </label> <span>${ beat.distance }</span>
					</div>
				</fieldset>
				<fieldset>
					<legend>Area Covered</legend>
					<div class="form-group">
						<label>Areas : </label>
						<%
							String values = "";
						%>
						<c:forEach var="area" items="${beat.areas}">
							<%
								if (values.isEmpty()) {
										if ((Area) pageContext.getAttribute("area") != null
												&& ((Area) pageContext.getAttribute("area")).getName() != null) {
											values = values + ((Area) pageContext.getAttribute("area")).getName();
										}
									} else {
										values = values + ", ";
										if ((Area) pageContext.getAttribute("area") != null
												&& ((Area) pageContext.getAttribute("area")).getName() != null) {
											values = values + ((Area) pageContext.getAttribute("area")).getName();
										}
									}
							%>
						</c:forEach>
						<c:if test="${fn:length(beat.areas) gt 0}">
							<span><%=values%></span>
						</c:if>
						<c:if test="${fn:length(beat.areas) eq 0}">
							<span>None</span>
						</c:if>
					</div>
				</fieldset>
			</div>
		</div>
	</div>
</body>
</html>
