<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<html lang="en">

<head>
    <title>Orders</title>
    <!-- Bootstrap Core CSS -->
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
    
    .the-table {
    table-layout: fixed;
    word-wrap: break-word;
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
        <%@ include file="menus.jsp" %>
       	<div class="row customer_list">
       		<div class="col-md-8">
           		<h2>Orders List</h2>   
           	</div>
        	<div class="col-md-4 add_customer">
        	</div>
		</div>        
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Order Booking ID</th>
                    <th>Customer Name</th>
                    <th>No Of Line Items</th>
                    <th>Order Value(Aprox.)</th>
                    <th>Status</th>
                    <th>Remarks</th>
                    <th>Creation Date</th>
                </tr>
            </thead>
            <tbody>
            	<c:forEach var="order" items="${orders}">  
                <tr>
                 	<td>${order.orderID}</td>
                	<td>${order.orderBookingID}</td>
                	<td>${order.customerName}</td>
                    <td>${order.noOfLineItems}</td>
                    <td>${order.bookValue}</td>
                    <td>${order.statusAsString}</td>
                    <td>${order.remark}</td>
                    <td>${order.dateCreatedString}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
   	</div>
</body>

</html>
