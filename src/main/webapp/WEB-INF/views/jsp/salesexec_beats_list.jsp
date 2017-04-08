<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ page import="com.sales.crm.model.Beat" %>

<html lang="en">

<head>
    <title>Roles</title>
    <!-- Bootstrap Core CSS -->
  	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
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
        	<div class="row customer_list">
        		<div class="col-md-8">
            		<h2>Sales Executive and Beats</h2>   
            	</div>
            	<div class="col-md-4 add_customer">
						<button type="submit" class="btn btn-primary"
					onclick="location.href='<%=request.getContextPath()%>/web/salesExecWeb/assignBeatForm';">
					Assign Beats To Sales Executive</button>	
				</div>
	        </div>        
            <table class="table">
                <thead>
                    <tr>
                        <th>Sales Executive Name</th>
                        <th>Assigned Beats</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="salesExec" items="${salesExecs}">  
                    <tr>
                   		<td>${salesExec.firstName} ${salesExec.lastName}</td>
                        <% String values=""; %>
						<c:forEach var="beat" items="${salesExec.beats}">
  								<%
  									if(values.isEmpty()){
  										if((Beat)pageContext.getAttribute("beat") != null  && ((Beat)pageContext.getAttribute("beat")).getDescription() != null){
  											values = values+ ((Beat)pageContext.getAttribute("beat")).getDescription();
  										}
  									}else{
  										values = values + " ,";
  										if((Beat)pageContext.getAttribute("beat") != null  && ((Beat)pageContext.getAttribute("beat")).getDescription() != null){
  											values = values+ ((Beat)pageContext.getAttribute("beat")).getDescription();
  										}
  									}
  								%>
						</c:forEach>
						<td><%= values %></td>
						<td><a href="<%=request.getContextPath()%>/web/salesExecWeb/assignBeatEditForm/${salesExec.userID}">Edit</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
</body>

</html>
