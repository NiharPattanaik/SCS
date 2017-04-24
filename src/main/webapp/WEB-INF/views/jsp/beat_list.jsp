<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.sales.crm.model.Area"%>
<html lang="en">

<head>
	<title>Beats</title>
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
				<div class="col-md-8">
					<h2>Beats List</h2>
				</div>
				<div class="col-md-4 add_customer">
					<button type="submit" class="btn btn-primary"
						onclick="location.href='<%=request.getContextPath()%>/web/beatWeb/createBeatForm';">Add
						New Beat</button>
				</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Description</th>
						<th>Coverage Schedule</th>
						<th>Distance</th>
						<th>Areas Covered</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="beat" items="${beats}">
						<tr>
							<td><a
								href="<%=request.getContextPath()%>/web/beatWeb/${beat.beatID}">${beat.beatID}</a></td>
							<td>${beat.name}</td>
							<td>${beat.description}</td>
							<td>${beat.coverageSchedule}</td>
							<td>${beat.distance}</td>
							<% String values=""; %>
							<c:forEach var="area" items="${beat.areas}">
								<%
  									if(values.isEmpty()){
  										if((Area)pageContext.getAttribute("area") != null  && ((Area)pageContext.getAttribute("area")).getDescription() != null){
  											values = values+ ((Area)pageContext.getAttribute("area")).getDescription();
  										}
  									}else{
  										values = values + " ,";
  										if((Area)pageContext.getAttribute("area") != null  && ((Area)pageContext.getAttribute("area")).getDescription() != null){
  											values = values+ ((Area)pageContext.getAttribute("area")).getDescription();
  										}
  									}
  								%>
							</c:forEach>
							<td><%= values %></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
</body>

</html>
