<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ page import="com.sales.crm.model.SalesExecBeatCustomer" %>
<%@ page import="com.sales.crm.model.Beat" %>
<%@ page import="com.sales.crm.model.TrimmedCustomer" %>
<%@ page import="com.sales.crm.model.SalesExecBeatCustomer" %>

<html lang="en">

<head>
    <title>Schedules Sales Executives Visit</title>
    <meta charset="UTF-8">
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
    
    .customer_list{
    margin-bottom:20px;
    }
    .add_customer{
    text-align:right;
    margin-top:31px;
    }
    
    .side_nav_btns{
    
    margin-top:10px;
    }
    
    .side_nav_btns a{
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
				<form:form modelAttribute="salesExecBeatCustomer" method="post"
					action="/crm/web/salesExecWeb/scheduleVisit">
					<fieldset>
						<legend>Sales Executive Visit Schedule of today</legend>
						<div class="form-group">
								<label>Sales Executive</label>
								<form:select path="salesExecutiveID" cssClass="form-control" id="sales_exec">
									<form:option value="-1" label="--- Select Sales Executive---" />
									<c:forEach var="salesExec" items="${salesExecs}">
										<form:option value="${ salesExec.userID }" label="${ salesExec.firstName } ${ salesExec.lastName }" id="id_trial"/>
									</c:forEach>
								</form:select>
						
						<div class="form-group">
							<label>Beats</label>
							<form:select path="beatID" cssClass="form-control" id="beats">
								<option value="-1" label="--- Select Beat---" />
							</form:select>
						</div>
						
						<div class="form-group">
							<label>Customers</label>
							<form:select path="customerIDs" cssClass="form-control" id="customers" multiple="true">
								<option value="-1" label="--- Select Beat---" />
							</form:select>
						</div>
					</div>
					</fieldset>
					<div class="form_submit">
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#sales_exec').change(function() {
					$.ajax({
							type : "GET",
							url : "/crm/rest/salesExecReST/"+$('#sales_exec').val(),
							dataType : "json",
							success : function(data) {
								$('#beats').empty();
								$.each(data,function(i,obj) {
									var div_data = "<option value="+obj.beatID+">"+ obj.name+ "</option>";
									$(div_data).appendTo('#beats');
								});
							}
						});
					});
				});
			
			$(document).ready(function() {
				$('#beats').change(function() {
					$.ajax({
							type : "GET",
							url : "/crm/rest/beatReST/"+$('#beats').val(),
							dataType : "json",
							success : function(data) {
								$('#customers').empty();
								$.each(data,function(i,obj) {
									var div_data =  "<option value="+obj.customerID+">"+ obj.customerName+ "</option>";
									$(div_data).appendTo('#customers');
								});
							}
						});
					});
				});
		</script>
</body>

</html>
