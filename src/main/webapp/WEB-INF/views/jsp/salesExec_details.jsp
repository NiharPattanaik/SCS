<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
            <a href="<%=request.getContextPath()%>/salesexecWeb/list/13">Sales Executive</a>
            <!-- Links -->
        </div>
		<div class="row top-height">
			<div class="col-md-8 ">
				<div class="row customer_list">
					<div class="col-md-8">
						<h2>Sales Executive Details</h2>
					</div>
					<div class="col-md-4 add_customer">
							<button type="submit" class="btn btn-primary" onclick="location.href='<%=request.getContextPath()%>/salesexecWeb/editSalesExecForm/${salesExec.salesExecID}';">Modify Sales Executive</button>
					</div>
				</div>
					<fieldset>
						<legend>Sales Executive Details</legend>
						<div class="form-group">
							<label>Name : </label>
							<label>${salesExec.name}</label>
						</div>
						<div class="form-group">
							<label>Description : </label>
							<label>${salesExec.description}</label>
						</div>
					</fieldset>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$('#dp').datepicker({format: 'dd/mm/yyyy'});
</script>

</html>
