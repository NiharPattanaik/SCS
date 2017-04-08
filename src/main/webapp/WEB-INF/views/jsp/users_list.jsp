<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ page import="com.sales.crm.model.Role" %>
<html lang="en">

<head>
    <title>Users</title>
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
        <!-- div class="row top-height"-->
        	<div class="row customer_list">
        		<div class="col-md-8">
            		<h2>Users List</h2>   
            	</div>
	        	<div class="col-md-4 add_customer">
						<button type="submit" class="btn btn-primary" onclick="location.href='<%=request.getContextPath()%>/web/userWeb/createUserForm';">Add New User</button>
				</div>
			</div>        
            <table class="table">
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>User Name</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>EMail ID</th>
                        <th>Mobile No</th>
                        <th>Role</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="user" items="${users}">  
                    <tr>
                    	<td><a href="<%=request.getContextPath()%>/web/userWeb/${user.userID}">${user.userID}</a></td>
                        <td>${user.userName}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.emailID}</td>
                        <td>${user.mobileNo}</td>
                        <% String values=""; %>
						<c:forEach var="role" items="${user.roles}">
  								<%
  									if(values.isEmpty()){
  										if((Role)pageContext.getAttribute("role") != null  && ((Role)pageContext.getAttribute("role")).getDescription() != null){
  											values = values+ ((Role)pageContext.getAttribute("role")).getDescription();
  										}
  									}else{
  										values = values + " ,";
  										if((Role)pageContext.getAttribute("role") != null  && ((Role)pageContext.getAttribute("role")).getDescription() != null){
  											values = values+ ((Role)pageContext.getAttribute("role")).getDescription();
  										}
  									}
  								%>
						</c:forEach>
						<td><%= values %></td>
                        <c:if test="${user.status == 1}">
   							<td>Active</td>
						</c:if>
                     </tr>
                    </c:forEach>
                </tbody>
            </table>
        <!--  /div-->
    </div>
</body>

</html>
