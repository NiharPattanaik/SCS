<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ page import="com.sales.crm.model.TrimmedCustomer" %>
<%@ page import="com.sales.crm.model.Beat" %>

<html lang="en">

<head>
    <title>Beat customers</title>
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
        <%@ include file="menus.jsp" %>
        	<div class="row customer_list">
        		<div class="col-md-8">
            		<h2>Beat and Customers</h2>   
            	</div>
            	<div class="col-md-4 add_customer">
            		<% if(resourcePermIDs.contains(ResourcePermissionEnum.BEAT_ASSOCIATE_CUSTOMERS.getResourcePermissionID())) { %>
						<button type="submit" class="btn btn-primary"
							onclick="location.href='<%=request.getContextPath()%>/web/beatWeb/assignCustomerForm';">
							Assign Customer To Beat</button>
					<% } %>	
				</div>
	        </div>        
            <table class="table">
                <thead>
                    <tr>
                        <th>Beat Name</th>
                        <th>Assigned Customer</th>
                        <% if(resourcePermIDs.contains(ResourcePermissionEnum.BEAT_EDIT_ASSOCIATED_CUSTOMERS.getResourcePermissionID())) { %>
                       		<th></th>
                       	<% } %>	
                       	
                       	<% if(resourcePermIDs.contains(ResourcePermissionEnum.BEAT_DELETE_ASSOCIATED_CUSTOMERS.getResourcePermissionID())) { %>
                        	<th></th>
                        <% } %>	
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="beat" items="${beats}">
                	<%
                		if(((Beat)pageContext.getAttribute("beat")).getCustomers() !=  null &&
                				((Beat)pageContext.getAttribute("beat")).getCustomers().size() > 0){
                			
                	%>  
                    <tr>
                   		<td>${beat.name}</td>
                        <% String values=""; %>
						<c:forEach var="customer" items="${beat.customers}">
  								<%
  									if(values.isEmpty()){
  										if((TrimmedCustomer)pageContext.getAttribute("customer") != null  && ((TrimmedCustomer)pageContext.getAttribute("customer")).getCustomerName() != null){
  											values = values+ ((TrimmedCustomer)pageContext.getAttribute("customer")).getCustomerName();
  										}
  									}else{
  										values = values + " ,";
  										if((TrimmedCustomer)pageContext.getAttribute("customer") != null  && ((TrimmedCustomer)pageContext.getAttribute("customer")).getCustomerName() != null){
  											values = values+ ((TrimmedCustomer)pageContext.getAttribute("customer")).getCustomerName();
  										}
  									}
  								%>
						</c:forEach>
						<td><%= values %></td>
						<% if(resourcePermIDs.contains(ResourcePermissionEnum.BEAT_EDIT_ASSOCIATED_CUSTOMERS.getResourcePermissionID())) { %>
							<td><a href="<%=request.getContextPath()%>/web/beatWeb/assignedBeatCustomerEditForm/${beat.beatID}">Edit</a></td>
						<% } %>	
						
						<% if(resourcePermIDs.contains(ResourcePermissionEnum.BEAT_DELETE_ASSOCIATED_CUSTOMERS.getResourcePermissionID())) { %>
							<td><a href="<%=request.getContextPath()%>/web/beatWeb/deleteAssignedBeatCustomerLink/${beat.beatID}">Delete</a></td>
						<% } %>	
                    </tr>
                    <% 
                		}
                    %>
                    </c:forEach>
                </tbody>
            </table>
        </div>
</body>

</html>
