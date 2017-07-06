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
    
    table.table.table-striped thead {
    background: #ddd;
    padding: 10px 0 10px 0;
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
            		<h2>Sales Executive and Beats</h2>   
            	</div>
            	<div class="col-md-4 add_customer">
            		<% if(resourcePermIDs.contains(ResourcePermissionEnum.USER_ASSIGN_BEATS.getResourcePermissionID())) { %>
						<button type="submit" class="btn btn-primary"
							onclick="location.href='<%=request.getContextPath()%>/web/salesExecWeb/assignBeatForm';">
							Assign Beats To Sales Executive</button>
					<% } %>			
				</div>
	        </div>        
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Sales Executive Name</th>
                        <th>Assigned Beats</th>
                        <% if(resourcePermIDs.contains(ResourcePermissionEnum.USER_EDIT_ASSIGNED_BEATS.getResourcePermissionID())) { %>
                        	<th></th>
                        <% } %>	
                        
                        <% if(resourcePermIDs.contains(ResourcePermissionEnum.USER_DELETE_ASSIGNED_BEATS.getResourcePermissionID())) { %>
                        	<th></th>
                        <% } %>	
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
  										if((Beat)pageContext.getAttribute("beat") != null  && ((Beat)pageContext.getAttribute("beat")).getName() != null){
  											values = values+ ((Beat)pageContext.getAttribute("beat")).getName();
  										}
  									}else{
  										values = values + " ,";
  										if((Beat)pageContext.getAttribute("beat") != null  && ((Beat)pageContext.getAttribute("beat")).getName() != null){
  											values = values+ ((Beat)pageContext.getAttribute("beat")).getName();
  										}
  									}
  								%>
						</c:forEach>
						<td><%= values %></td>
						<% if(resourcePermIDs.contains(ResourcePermissionEnum.USER_EDIT_ASSIGNED_BEATS.getResourcePermissionID())) { %>
							<td><a href="<%=request.getContextPath()%>/web/salesExecWeb/assignBeatEditForm/${salesExec.userID}">Edit</a></td>
						<% } %>	
						<% if(resourcePermIDs.contains(ResourcePermissionEnum.USER_DELETE_ASSIGNED_BEATS.getResourcePermissionID())) { %>
							<td><a href="<%=request.getContextPath()%>/web/salesExecWeb/deleteBeatsAssignment/${salesExec.userID}">Delete</a></td>
						<% } %>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
</body>

</html>
