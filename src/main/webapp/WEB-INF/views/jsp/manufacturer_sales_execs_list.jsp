<!DOCTYPE html>
<%@page import="com.sales.crm.model.SalesExecutive"%>
<%@page import="com.sales.crm.service.SalesExecService"%>
<%@page import="com.sales.crm.model.Manufacturer"%>
<%@page import="com.sales.crm.model.Customer"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ page import="com.sales.crm.model.TrimmedCustomer" %>
<%@ page import="com.sales.crm.model.Beat" %>

<html lang="en">

<head>
    <title>Manufacturer Sales Executives</title>
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
            		<h2>Manufacturer and Sales Executives</h2>   
            	</div>
            	<div class="col-md-4 add_customer">
            			<button type="submit" class="btn btn-primary"
							onclick="location.href='<%=request.getContextPath()%>/web/manufacturerWeb/assignSalesExecutiveForm';">
							Assign Sales Executives To Manufacturer</button>
				</div>
	        </div>        
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Manufacturer Name</th>
                        <th>Sales Executives</th>
                       	<th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="manufacturer" items="${manufacturers}">
                	<%
                		if(((Manufacturer)pageContext.getAttribute("manufacturer")).getSalesExecs() !=  null &&
                				((Manufacturer)pageContext.getAttribute("manufacturer")).getSalesExecs().size() > 0){
                			
                	%>  
                    <tr>
                   		<td>${manufacturer.name}</td>
                        <% String values=""; %>
						<c:forEach var="salesExec" items="${manufacturer.salesExecs}">
  								<%
  									if(values.isEmpty()){
  										if((SalesExecutive)pageContext.getAttribute("salesExec") != null  && ((SalesExecutive)pageContext.getAttribute("salesExec")).getName() != null){
  											values = values+ ((SalesExecutive)pageContext.getAttribute("salesExec")).getName();
  										}
  									}else{
  										values = values + " ,";
  										if((SalesExecutive)pageContext.getAttribute("salesExec") != null  && ((SalesExecutive)pageContext.getAttribute("salesExec")).getName() != null){
  											values = values+ ((SalesExecutive)pageContext.getAttribute("salesExec")).getName();
  										}
  									}
  								%>
						</c:forEach>
						<td><%= values %></td>
						<td><a href="<%=request.getContextPath()%>/web/manufacturerWeb/assignSalesExecEditForm/${manufacturer.manufacturerID}">Edit</a></td>
						
						<td><a href="<%=request.getContextPath()%>/web/manufacturerWeb/deleteAassignedSalesexec/${manufacturer.manufacturerID}">Delete</a></td>
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
