<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ page import="com.sales.crm.model.Beat" %>
<%@ page import="com.sales.crm.model.TrimmedCustomer" %>

<html lang="en">

<head>
    <title>Delivery Booking</title>
  	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet" />
	<script src="<%=request.getContextPath()%>/resources/js/jquery-3.2.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<link href="<%=request.getContextPath()%>/resources/css/bootstrap-datepicker.css" rel="stylesheet">
	<script	src="<%=request.getContextPath()%>/resources/js/bootstrap-datepicker.js"></script>

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
    
    fieldset {
	border: 1px solid grey;
	padding: 10px;
	border-radius: 5px;
}

legend {
	width: auto !important;
	border-bottom: 0px !important;
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
        		<div class="col-md-4">
            		<h3>Scheduled Delivery Bookings</h3>   
            	</div>
	        	<div class="col-md-4 add_customer">
	        		 <% if(resourcePermIDs.contains(ResourcePermissionEnum.USER_SCHEDULE_VISIT.getResourcePermissionID())) { %>
						<button type="submit" class="btn btn-primary" onclick="location.href='<%=request.getContextPath()%>/web/deliveryExecWeb/scheduleDeliveryBookingForm';">Schedule Delivery Booking</button>
					<% } %>
				</div>
			</div>  
				<div class="row top-height">
		        	<div class="col-md-8 ">
						<form:form modelAttribute="deliveryBookingSchedule" method="post"
							action="/crm/web/deliveryExecWeb/unscheduleDeliveryBooking">
								<div class="form-group">
									<label>Visit Date</label>
									<form:input id="dp" name="visitDate" cssClass="dp form-control"
										path="visitDate" />
								</div>
								<div class="form-group">
										<label>Delivery Executive</label>
										<form:select path="delivExecutiveID" cssClass="form-control" id="deliv_exec">
										</form:select>
								</div>
								<div class="form-group">
									<label>Beats</label>
									<select class="form-control" id="beats">
										<option value="-1" label="--- Select Beat---" />
									</select>
								</div>
								
								<div id="customer-table">
									<fieldset>
										<legend>Customers</legend>
										<div class="form-group">
											<table class="table" id="myTable">
									            <thead>
									                <tr>
									                    <th>Customer Name</th>
									                    <th>Order Reference</th>
									                    <th>Select to Cancel Visit</th>
									                    <th><button type="submit" class="btn btn-primary" id="cancel">Cancel Visits</button></th>
									             	</tr>
									            </thead>
									            <tbody>
									            </tbody>
								        	</table>	
										</div>
									</fieldset>	
								</div>
						</form:form>
					</div>
				</div>
			</div>
		<script type="text/javascript">
		//Sales Execs
		$(document).ready(function() {
			$('#customer-table').hide();
			$('#dp').blur(function() {
				if( $('#dp').val() ) {
					$.ajax({ 
						type : "GET",
						url : "/crm/rest/deliveryExecReST/list/"+$('#dp').val(),
						dataType : "json",
						success : function(data) {
							$('#deliv_exec').empty();
							var div_data1="<option value=\"-1\" label=\"--- Select Delivery Executive--- \"/>";
							$(div_data1).appendTo('#deliv_exec');
							$.each(data,function(i,obj) {
								var div_data = "<option value="+obj.userID+">"+ obj.name+ "</option>";
								$(div_data).appendTo('#deliv_exec');
							});
						}
					});
				}
			});
		});
		
		//Beats
		$(document).ready(function() {
			$('#deliv_exec').change(function() {
				$.ajax({ 
					type : "GET",
					url : "/crm/rest/deliveryExecReST/scheduledDeliveryBeats/"+$('#deliv_exec').val()+"/"+$('#dp').val(),
					dataType : "json",
					success : function(data) {
						$('#beats').empty();
						var div_data1="<option value=\"-1\" label=\"--- Select Beat--- \"/>";
						$(div_data1).appendTo('#beats');
						$.each(data,function(i,obj) {
							var div_data = "<option value="+obj.beatID+">"+ obj.name+ "</option>";
							$(div_data).appendTo('#beats');
						});
					}
				});
			});
		});
			
			//Customer
			$(document).ready(function() {
				$('#beats').change(function() {
					var isCusromerPresent = false;
					$.ajax({
							type : "GET",
							url : "/crm/rest/deliveryExecReST/deliveryScheduledCustomers/"+$('#deliv_exec').val()+"/"+$('#dp').val()+"/"+$('#beats').val(),
							dataType : "json",
							success : function(data) {
								$("#myTable > tbody").empty();
								$.each(data,function(i,obj) {
									isCusromerPresent = true;
									var row_data = "<tr><td>"+ obj.customer.customerName +"</td><td><a href=# id=order-link data-toggle=modal data-target=#orders data-rows=" + JSON.stringify(obj.orders) + "><i>View Linked Order</i></a></><td><input name=customerIDs id=customerIDs type=checkbox value="+obj.customer.customerID+"></td></tr>";
									$("#myTable > tbody").append(row_data);
								});
								//Show/Hide customer table
								if(isCusromerPresent == true){
									$('#customer-table').show();
								}else{
									$('#customer-table').hide();
								}
							}
						});
					});
				});
			
			$('#dp').datepicker({format: 'dd-mm-yyyy'});
			
			//Cancel visits
			$('#cancel').click(function(){
				$('#myForm').submit();
			});
			
			$(document).ready(function() {
			    $("#order-link a").click(function() {
			        //Do stuff when clicked
			    });
			});
			
		</script>
		
		<!-- Order List Modal -->
	<div class="modal fade" id="orders" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header"><b>Order included for the delivery</b></div>
				<div class="modal-body">
					   <table class="table" id="myTable">
				            <thead>
				                <tr>
				                	<th>Order ID</th>
				                    <th>Order Booking ID</th>
				                    <th>Order Date</th>
				                    <th>No Of Line Items</th>
				                    <th>Booking Value</th>
				                    <th>Remark</th>
				             	</tr>
				            </thead>
				            <tbody>
				            </tbody>
			        	</table>	
				</div>
				<div class="modal-custom-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
				</div>	
				<script type="text/javascript">
					var customerID;
					$('#orders').on('show.bs.modal', function (e) {
						$("#myTable > tbody").empty();
						var datarows = $(e.relatedTarget).data('rows');
						$.each( datarows, function( index, value ) {
							customerID = value.customerID;
							var row_data = "<tr><td>"+ value.orderID +"</td><td>"+ value.orderBookingID +"</td><td>"+ value.dateCreatedString +"</td><td>"+ value.noOfLineItems +"</td><td>"+ value.bookValue +"</td><td>"+ value.remark +"</td></tr>";
							$("#myTable > tbody").append(row_data);
						});
					});
				</script>
				
			</div>
		</div>
	</div>
	</body>
</html>
