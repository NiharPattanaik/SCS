<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">

<head>
	<title>Modify Beat</title>
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

.form-group.required .control-label:after { 
   content:"*";
   color:red;
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
				<h2>Modify Beat</h2>
				<form:form modelAttribute="beat" method="post"
					action="/crm/web/beatWeb/update">
					<fieldset>
						<legend>Beat Details</legend>
						<div class="form-group required">
							<label class='control-label'>Beat Name</label>
							<form:input name="name" cssClass="form-control" path="name" value="${ beat.name }"/>
						</div>
						<div class="form-group">
							<label>Description</label>
							<form:input name="description" cssClass="form-control"
								path="description" value="${ beat.description }"/>
						</div>
						<div class="form-group">
							<label>Coverage Schedule</label>
							<form:select path="coverageSchedule" cssClass="form-control" itemValue="${ beat.coverageSchedule }">
								<form:option value="-1" label="--- Select ---" />
								<form:option value="Daily" label="Daily" />
								<form:option value="Weekly" label="Weekly" />
								<form:option value="Fortnightly" label="Fortnightly" />
								<form:option value="Monthly" label="Monthly" />
								<form:option value="Other" label="Other" />
							</form:select>
						</div>
						<div class="form-group">
							<label>Distance</label>
							<form:input name="distance" cssClass="form-control" path="distance" value="${ beat.distance }"/>
						</div>
					</fieldset>
					<fieldset>
						<legend>Areas Covered</legend>
						<div class="form-group">
							<label>Areas</label>
							<form:select path="areas" cssClass="form-control" multiple="true">
								<form:option value="-1" label="--- Select ---" />
								<form:options items="${areas}" itemValue="areaID"
									itemLabel="name"/>
							</form:select>
						</div>
					</fieldset>
					<form:hidden path="beatID" name="beatID" value="${ beat.beatID }"/>
					<form:hidden path="resellerID" name="resellerID" value="${ beat.resellerID }"/>
					<div>
						<fmt:formatDate value="${ beat.dateCreated }" type="date"
								pattern="dd/MM/yyyy" var="createdDate" />
						<form:hidden path="dateCreated" value="${createdDate}"/>
					</div>
					<div>
						<fmt:formatDate value="${ beat.dateModified }" type="date"
								pattern="dd/MM/yyyy" var="modifiedDate" />
						<form:hidden path="dateModified" value="${modifiedDate}"/>
					</div>
					<div>
						<form:hidden path="companyID" value="${ beat.companyID }"/>
					</div>
					<div class="form_submit">
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
	   		$("#name").prop('required',true);
		});
	</script>
</body>

</html>
