<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
<title>Create User</title>
<!-- Bootstrap Core CSS -->
<link
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/resources/css/bootstrap-datepicker.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-1.12.4.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/bootstrap-datepicker.js"></script>

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
				<h2>Add New User</h2>
				<form:form modelAttribute="user" method="post"
					action="/crm/userWeb/save">
					<fieldset>
						<legend>User Details</legend>
						<div class="form-group">
							<label>First Name</label>
							<form:input name="firstName" cssClass="form-control" path="firstName" />
						</div>
						<div class="form-group">
							<label>Last Name</label>
							<form:input name="lastName" cssClass="form-control"
								path="lastName" />
						</div>
						<div class="form-group">
							<label>Description</label>
							<form:input name="description" cssClass="form-control"
								path="description" />
						</div>
						<div class="form-group">
							<label>Email ID</label>
							<form:input name="emailID" cssClass="form-control"
								path="emailID" />
						</div>
						<div class="form-group">
							<label>Mobile Number</label>
							<form:input name="mobileNo" cssClass="form-control"
								path="mobileNo" />
						</div>
					</fieldset>
					
					<fieldset>
						<legend>Login Details</legend>
						<div class="form-group">
							<label>User Name</label>
							<form:input name="userName" cssClass="form-control" path="userName" />
						</div>
						<div class="form-group">
							<label>Password</label>
							<form:input name="password" cssClass="form-control"
								path="password" />
						</div>
					</fieldset>
					
					<fieldset>
						<legend>Role Details</legend>
						<div class="form-group">
							<label>Roles</label>
							<form:select path="roles" cssClass="form-control">
								<form:option value="-1" label="--- Select ---" />
								<form:options items="${roles}" itemValue="roleID"
									itemLabel="roleName" />
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
</script>

</html>
