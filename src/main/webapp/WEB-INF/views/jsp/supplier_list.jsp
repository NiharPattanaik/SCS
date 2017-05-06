<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<html lang="en">

<head>
    <title>Suppliers</title>
    <!-- Bootstrap Core CSS -->
	 <meta charset="utf-8">
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
        	<div class="row customer_list">
        		<div class="col-md-8">
            		<h2>Suppliers List</h2>   
            	</div>
	        	<div class="col-md-4 add_customer">
	        		<% if(resourcePermIDs.contains(ResourcePermissionEnum.SUPPLIER_CREATE.getResourcePermissionID())) { %>
						<button type="submit" class="btn btn-primary" onclick="location.href='<%=request.getContextPath()%>/web/supplierWeb/createSupplierForm';">Add New Supplier</button>
					<% } %>
				</div>
			</div>        
            <table class="table">
                <thead>
                    <tr>
                        <th>Supplier ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>City</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="supplier" items="${suppliers}">  
                    <tr>
                    	<% if(resourcePermIDs.contains(ResourcePermissionEnum.SUPPLIER_READ.getResourcePermissionID())) { %>
                    		<td><a href="<%=request.getContextPath()%>/web/supplierWeb/${supplier.supplierID}">${supplier.supplierID}</a></td>
                    	<% } else { %>	
                    		<td>${supplier.supplierID}</td>
                    	<%  } %>
                        <td>${supplier.name}</td>
                        <td>${supplier.description}</td>
                        <td>${supplier.address[0].city}</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
</body>

</html>
