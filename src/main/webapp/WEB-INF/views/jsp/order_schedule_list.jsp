<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 
<%@ page import="com.sales.crm.model.OrderBookingSchedule" %>
<%@ page import="com.sales.crm.model.Beat" %>
<%@ page import="com.sales.crm.model.TrimmedCustomer" %>

<html lang="en">

<head>
    <title>Order Booking</title>
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

.customer_list {
	margin-bottom: 20px;
}

.add_customer {
	text-align: right;
	margin-top: 31px;
}

.side_nav_btns {
	margin-top: 10px;
}

.side_nav_btns a {
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

table.table.table-striped thead {
	background: #ddd;
	padding: 10px 0 10px 0;
}

.table {
	width: 100%;
	max-width: 100%;
	margin-bottom: 20px;
	margin-top: 10px;
	margin-left: 15px
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
            		<h3>Scheduled Order Bookings</h3>   
            	</div>
	        	<div class="col-md-4 add_customer">
	        		 <% if(resourcePermIDs.contains(ResourcePermissionEnum.USER_SCHEDULE_VISIT.getResourcePermissionID())) { %>
						<button type="submit" class="btn btn-primary" onclick="location.href='<%=request.getContextPath()%>/web/orderWeb/scheduleOrderBookingForm';">Schedule Order Booking</button>
					<% } %>
				</div>
			</div>  
				<div class="row top-height">
		        	<div class="col-md-8 ">
						<form:form modelAttribute="orderBookingSchedule" class="form-horizontal">
								<div class="form-group">
									<label class="col-sm-3">Visit Date</label>
									<div class="col-sm-4">
										<form:input id="dp" name="visitDate" cssClass="dp form-control"
											path="visitDate" />
									</div>		
								</div>
								<div class="form-group">
										<label class="col-sm-3">Sales Executive</label>
										<div class="col-sm-4">
											<form:select path="salesExecutiveID" cssClass="form-control" id="sales_exec">
												<option value="-1" label="--- Select Beat---" />
											</form:select>
										</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3">Beats</label>
									<div class="col-sm-4">
										<select class="form-control" id="beats">
											<option value="-1" label="--- Select Beat---" />
										</select>
									</div>	
								</div>
								<div>
									<button type="button" class="btn btn-primary" id="search" disabled>Search</button>
								</div>
								<div id="customer-table">
										<div class="form-group">
											<table class="table table-striped" id="myTable">
									            <thead>
									                <tr>
									                    <th>Customer Name</th>
									                    <th>Beat</th>
									                    <th>Sales Executive</th>
									                    <th>Action</th>
									                </tr>
									            </thead>
									            <tbody>
									            	<c:if test="${fn:length(orderBookedSchedules) gt 0}">
														<c:forEach var="orderBookedSchedule" items="${orderBookedSchedules}">  
									               		 <tr>
										                 	<td>${orderBookedSchedule.customerName}</td>
										                    <td>${orderBookedSchedule.beatName}</td>
										                    <td>${orderBookedSchedule.salesExecName}</td>
										                    <td><a href="#" id="link" data-params=${orderBookedSchedule.bookingScheduleID}>Cancel</a></td>
									                	</tr>
									                	</c:forEach>
													</c:if>
													<c:if test="${fn:length(orderBookedSchedules) eq 0}">
														<tr><td>No order booking is scheduled.</td><td></td><td></td><td></td></tr>
													</c:if>
									            </tbody>
								        	</table>	
										</div>
								</div>
						</form:form>
					</div>
				</div>
			</div>
		<script type="text/javascript">
		//Sales Execs
		$(document).ready(function() {
			
			$('#dp').datepicker({format: 'dd-mm-yyyy'});
			
			$('#dp').blur(function() {
				if( $('#dp').val() ) {
					$('#search').prop('disabled', false);
					$.ajax({ 
						type : "GET",
						url : "/crm/rest/salesExecReST/list/"+$('#dp').val(),
						dataType : "json",
						success : function(data) {
							$('#sales_exec').empty();
							var div_data1="<option value=\"-1\" label=\"--- Select Sales Executive--- \"/>";
							$(div_data1).appendTo('#sales_exec');
							$.each(data,function(i,obj) {
								var div_data = "<option value="+obj.userID+">"+ obj.name+ "</option>";
								$(div_data).appendTo('#sales_exec');
							});
						}
					});
				}else{
					$('#search').prop('disabled', true);
				}
			});
		
			//Beats
			$('#sales_exec').change(function() {
				$.ajax({ 
					type : "GET",
					url : "/crm/rest/salesExecReST/scheduledVisit/"+$('#sales_exec').val()+"/"+$('#dp').val(),
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
			
			$(document).on('click', "#link", function (e){
				var dataFound = 0;
				//Hack
				var date = "-";
				if($('#dp').val()){
					date=$('#dp').val();
				}
				 $.ajax({
						type : "GET",
						url : "/crm/rest/orderReST/unscheduleOrderBooking/"+$(this).data('params'),
						dataType : "json",
						success : function(data) {
							$("#successModal").modal('show');
							$.ajax({
								type : "GET",
								url : "/crm/rest/orderReST/scheduledVisit/"+$('#sales_exec').val()+"/"+date+"/"+$('#beats').val(),
								dataType : "json",
								success : function(data) {
									$("#myTable > tbody").empty();
									var result = data.businessEntities;
									$.each(result,function(i,obj) {
										dataFound = 1;
										var row_data = "<tr><td>"+obj.customerName+"</td><td>"+obj.beatName+"</td><td>"+obj.salesExecName+"</td><td><a href=# id=link data-params="+obj.bookingScheduleID+">Cancel</a></td></tr>";
						                $("#myTable > tbody").append(row_data);
									});
									if(dataFound == 0){
										var row_data = "<br>No order booking is scheduled.";
										$("#myTable > tbody").append(row_data);
									}
								},
								error: function(jq,status,message) {
									$("#listModal").modal('show');
						        }
							});
						},
						error: function(jq,status,message) {
							$("#errorModal").modal('show');
				        }
				});
			}); 
			
			//Search visits
			$('#search').click(function(){
				var dataFound = 0;
				//Hack
				var date = "-";
				if($('#dp').val()){
					date=$('#dp').val();
				}
				$.ajax({
						type : "GET",
						url : "/crm/rest/orderReST/scheduledVisit/"+$('#sales_exec').val()+"/"+date+"/"+$('#beats').val(),
						dataType : "json",
						success : function(data) {
							$("#myTable > tbody").empty();
							var result = data.businessEntities;
							$.each(result,function(i,obj) {
								dataFound = 1;
								var row_data = "<tr><td>"+obj.customerName+"</td><td>"+obj.beatName+"</td><td>"+obj.salesExecName+"</td><td><a href=# id=link data-params="+obj.bookingScheduleID+">Cancel</a></td></tr>";
				                $("#myTable > tbody").append(row_data);
							});
							if(dataFound == 0){
								var row_data = "<br>No order booking is scheduled.";
								 $("#myTable > tbody").append(row_data);
							}
						},
						error: function(jq,status,message) {
							$("#listModal").modal('show');
				        }
					});
			});
			
		});
		</script>

	<div class="modal fade" id="successModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<b>Success</b>
				</div>
				<div class="modal-body">Scheduled order booking has been
					successfully canceled.</div>
				<div class="modal-custom-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="errorModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<b>Error !!</b>
				</div>
				<div class="modal-body">Scheduled order booking can't be
					successfully canceled. Please try again after sometime, if error
					persists contact System Administrator.</div>
				<div class="modal-custom-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="listModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<b>Error !!</b>
				</div>
				<div class="modal-body">Scheduled order booking list could not be fetched
					successfully. Please try again after sometime, if error
					persists contact System Administrator.</div>
				<div class="modal-custom-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>
