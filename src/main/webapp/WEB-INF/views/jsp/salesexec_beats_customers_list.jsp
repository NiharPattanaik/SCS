<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ page import="com.sales.crm.model.SalesExecBeatCustomer" %>
<%@ page import="com.sales.crm.model.Beat" %>
<%@ page import="com.sales.crm.model.TrimmedCustomer" %>
<%@ page import="com.sales.crm.model.SalesExecBeatCustomer" %>

<html lang="en">

<head>
    <title>Sales Executive - Beats - Customers</title>
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
            		<h2></h2>   
            	</div>
	        	<div class="col-md-4 add_customer">
						<button type="submit" class="btn btn-primary" onclick="location.href='<%=request.getContextPath()%>/web/salesExecWeb/salesExecScheduleForm';">Schedule a Visit</button>
				</div>
			</div>  
        	<div class="col-md-8 ">
				<form:form modelAttribute="salesExecutive" method="post"
					action="/crm/salesExecWeb/assignBeat">
					<fieldset>
						<legend>Scheduled Sales Executive Visits</legend>
						<div class="form-group">
							<label>Visit Date</label>
							<form:input id="dp" name="visitDate" cssClass="dp form-control"
								path="visitDate" />
						</div>
						<div class="form-group">
								<label>Sales Executive</label>
								<form:select path="userID" cssClass="form-control" id="sales_exec">
								</form:select>
						</div>
						<div class="form-group">
							<label>Beats</label>
							<select class="form-control" id="beats">
								<option value="-1" label="--- Select Beat---" />
							</select>
						</div>
						
						<div class="form-group">
							<label>Customers</label>
							<div>
								<ul id="customers"></ul>
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
			</div>
		<script type="text/javascript">
		//Sales Execs
		$(document).ready(function() {
			$('#dp').blur(function() {
				if( $('#dp').val() ) {
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
								console.log(div_data);
								$(div_data).appendTo('#sales_exec');
							});
						}
					});
				}
			});
		});
		
		//Beats
		$(document).ready(function() {
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
		});
			
			//Customer
			$(document).ready(function() {
				$('#beats').change(function() {
					$.ajax({
							type : "GET",
							url : "/crm/rest/salesExecReST/scheduledVisit/"+$('#sales_exec').val()+"/"+$('#dp').val()+"/"+$('#beats').val(),
							dataType : "json",
							success : function(data) {
								$('#customers').empty();
								$.each(data,function(i,obj) {
									console.log(obj.customerName);
									var div_data =  "<li>"+obj.customerName+"</li>";
									$(div_data).appendTo('#customers');
								});
							}
						});
					});
				});
			
			$('#dp').datepicker({format: 'dd-mm-yyyy'});
			
		</script></body>

</html>
