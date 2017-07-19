<!DOCTYPE html>
<%@page import="com.sales.crm.model.Manufacturer"%>
<%@page import="com.sales.crm.model.Supplier"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ page import="com.sales.crm.model.TrimmedCustomer" %>
<%@ page import="com.sales.crm.model.Beat" %>

<html lang="en">

<head>
    <title>Supplier Manufacturer</title>
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
        <!-- Links -->
        <%@ include file="menus.jsp" %>
        	<div class="row customer_list">
        		<div class="col-md-8">
            		<h2>Supplier and Manufacturer</h2>   
            	</div>
            	<div class="col-md-4 add_customer">
            			<button type="submit" class="btn btn-primary"
							onclick="location.href='<%=request.getContextPath()%>/web/supplierWeb/assignManufacturerForm';">
							Assign Manufacture To Supplier</button>
				</div>
	        </div>        
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Supplier Name</th>
                        <th>Manufacturers</th>
                        <th></th>
                       	<th></th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="supplier" items="${suppliers}">
                	<%
                		if(((Supplier)pageContext.getAttribute("supplier")).getManufacturers() !=  null &&
                				((Supplier)pageContext.getAttribute("supplier")).getManufacturers().size() > 0){
                			
                	%>  
                    <tr>
                   		<td>${supplier.name}</td>
                        <% String values=""; %>
						<c:forEach var="manufacturer" items="${supplier.manufacturers}">
  								<%
  									if(values.isEmpty()){
  										if((Manufacturer)pageContext.getAttribute("manufacturer") != null  && ((Manufacturer)pageContext.getAttribute("manufacturer")).getFullName() != null){
  											values = values+ ((Manufacturer)pageContext.getAttribute("manufacturer")).getFullName();
  										}
  									}else{
  										values = values + " ,";
  										if((Manufacturer)pageContext.getAttribute("manufacturer") != null  && ((Manufacturer)pageContext.getAttribute("manufacturer")).getFullName() != null){
  											values = values+ ((Manufacturer)pageContext.getAttribute("manufacturer")).getFullName();
  										}
  									}
  								%>
						</c:forEach>
						<td><%= values %></td>
						<td><a href="<%=request.getContextPath()%>/web/supplierWeb/assignManufacturerEditForm/${supplier.supplierID}">Edit</a></td>
						<td><a href=# data-toggle=modal data-target=#confirm data-suppid=${supplier.supplierID}>Delete</a></td>
					</tr>
                    <% 
                		}
                    %>
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
					Are you sure you want to remove the Supplier-Manufacturer Mapping ?
				</div>
				<div class="modal-custom-footer">
					<button type="submit" id="modalSubmit" class="btn btn-primary">Confirm</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
					<script type="text/javascript">
						var suppid="";
						$('#confirm').on('show.bs.modal', function (e) {
							suppid = $(e.relatedTarget).data('suppid');
						});
						$('#modalSubmit').click(function(){
						   window.location.href = "/crm/web/supplierWeb/deleteAassignedManufacturer/"+suppid
						});
					</script>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
