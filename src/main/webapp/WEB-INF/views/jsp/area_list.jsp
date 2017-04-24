<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ page import="com.sales.crm.model.Role" %>
<html lang="en">

<head>
    <title>Areas</title>
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
        <%@ include file="menus.jsp" %>
        <div class="row top-height">
        	<div class="row customer_list">
        		<div class="col-md-8">
            		<h2>Area List</h2>   
            	</div>
	        	<div class="col-md-4 add_customer">
						<button type="submit" class="btn btn-primary" onclick="location.href='<%=request.getContextPath()%>/web/areaWeb/createAreaForm';">Add New Area</button>
				</div>
			</div>        
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Word No.</th>
                        <th>Pin Code</th>
                  </tr>
                </thead>
                <tbody>
                	<c:forEach var="area" items="${areas}">  
                    <tr>
                    	<td><a href="<%=request.getContextPath()%>/web/areaWeb/${area.areaID}">${area.areaID}</a></td>
                        <td>${area.name}</td>
                        <td>${area.description}</td>
                        <td>${area.wordNo}</td>
                        <td>${area.pinCode}</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>

</html>
