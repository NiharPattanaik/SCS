<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<html lang="en">

<head>
    <title>Page one</title>
    <!-- Bootstrap Core CSS -->
    <!-- link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"-->
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
        <div class="row pull-right side_nav_btns">
            <a href="<%=request.getContextPath()%>/resellerWeb/13">Profile</a>
            <a href="<%=request.getContextPath()%>/customerWeb/list/13">Customers</a>
            <a href="<%=request.getContextPath()%>/salesexecWeb/list/13">Sales Executive</a>
            <!-- Links -->
        </div>
        <div class="row top-height">
        	<div class="row customer_list">
        		<div class="col-md-8">
            		<h2>Sales Executive List</h2>   
            	</div>
	        	<div class="col-md-4 add_customer">
						<button type="submit" class="btn btn-primary" onclick="location.href='<%=request.getContextPath()%>/salesexecWeb/createSalesExecForm';">Add New Sales Executive</button>
			</div>
			</div> 
        	<table class="table">
                <thead>
                    <tr>
                        <th>Sales Executive ID</th>
                        <th>Name</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="salesExec" items="${salesExecs}">  
                    <tr>
                    	<td><a href="<%=request.getContextPath()%>/salesexecWeb/${salesExec.salesExecID}">${salesExec.salesExecID}</a></td>
                        <td>${salesExec.name}</td>
                        <td>Test</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>

</html>
