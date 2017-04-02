<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
<title>Modify Sales Executive</title>
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
				<h2>Modify Sales Executive Details</h2>
				<form:form modelAttribute="salesExec" method="post"
					action="/crm/salesexecWeb/update">
					<fieldset>
						<legend>Sales Executive Details</legend>
						<form:hidden name="salesExecID" path="salesExecID" value="${ salesExec.salesExecID }"/>
						<form:hidden name="resellerID" path="resellerID" value="${ salesExec.resellerID }"/>
						<div class="form-group">
							<label>Name</label>
							<form:input name="name" cssClass="form-control" path="name" value="${salesExec.name}"/>
						</div>
						<div class="form-group">
							<label>Description</label>
							<form:input name="description" cssClass="form-control"
								path="description" value="${salesExec.description}"/>
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
