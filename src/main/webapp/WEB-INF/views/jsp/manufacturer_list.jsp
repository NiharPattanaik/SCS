<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<html lang="en">

<head>
    <title>Manufacturers</title>
    <!-- Bootstrap Core CSS -->
	 <meta charset="utf-8">
	<link href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet" />
	<script src="<%=request.getContextPath()%>/resources/js/jquery-3.2.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.dataTables.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/dataTables.bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/css/dataTables.bootstrap.min.css"></script>
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
	
	.dataTables_paginate {
    margin-top: -20px;
    text-align: right;
    float: right;
    display: block
   }
	
	.modal-custom-footer {
    padding: 15px;
    text-align: center;
    border-top: 1px solid #e5e5e5;
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
            		<h2>Manufacturer List</h2>   
            	</div>
	        	<div class="col-md-4 add_customer">
	        			<button type="submit" class="btn btn-primary" onclick="location.href='<%=request.getContextPath()%>/web/manufacturerWeb/createManufacturerForm';">Add New Manufacturer</button>
				</div>
			</div>        
            <table class="table table-striped" id="manufactTable">
                <thead>
                    <tr>
                        <th>Manufacturer ID</th>
                        <th>Short Name</th>
                        <th>Full Name</th>
                        <th>Division</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="manufacturer" items="${manufacturers}">  
                    <tr>
                    	<td>${manufacturer.manufacturerID}</td>
                    	<td>${manufacturer.shortName}</td>
                        <td>${manufacturer.fullName}</td>
                        <td>${manufacturer.division}</td>
                        <td><a href="<%=request.getContextPath()%>/web/manufacturerWeb/editManufacturerForm/${manufacturer.manufacturerID}">Edit</a></td>
                        <!-- td><a href="<%=request.getContextPath()%>/web/manufacturerWeb/delete/${manufacturer.manufacturerID}">Delete</a></td-->
                        <td><a href=# id=order-link data-toggle=modal data-target=#confirm data-manfname=${manufacturer.fullName} data-manfid=${manufacturer.manufacturerID}>Delete</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
      <div class="modal fade" id="confirm" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<b>Confirm !</b>
				</div>
				<div class="modal-body">
					Are you sure you want to remove the Manufacturer ?
				</div>
				<div class="modal-custom-footer">
					<button type="submit" id="modalSubmit" class="btn btn-primary">Confirm</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
					<script type="text/javascript">
						var manfid="";
						$('#confirm').on('show.bs.modal', function (e) {
							manfid = $(e.relatedTarget).data('manfid');
						});
						$('#modalSubmit').click(function(){
						   window.location.href = "/crm/web/manufacturerWeb/delete/"+manfid
						});
					</script>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
$(document).ready(function() {
    $('#manufactTable').DataTable({searching: false, aaSorting: [], bLengthChange: false, pageLength: 10});
} );

</script>

</html>
