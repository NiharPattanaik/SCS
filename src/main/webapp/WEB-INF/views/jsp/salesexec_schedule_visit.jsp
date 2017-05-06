<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.sales.crm.model.SalesExecBeatCustomer"%>
<%@ page import="com.sales.crm.model.Beat"%>
<%@ page import="com.sales.crm.model.TrimmedCustomer"%>
<%@ page import="com.sales.crm.model.SalesExecBeatCustomer"%>

<html lang="en">

<head>
<title>Schedules Sales Executives Visit</title>
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

.form_submit {
	margin-top: 14px;
	text-align: right;
}

.form-group.required .control-label:after { 
   content:"*";
   color:red;
}

</style>
</head>

<body>
	<!-- Header -->
	<header class="dpHeaderWrap">
		<div class="text-center">Header part</div>
	</header>
	<!-- Header -->
	<div class="container">
		<!-- Links -->
		<%@ include file="menus.jsp" %>
		<div class="row top-height">
			<div class="col-md-8 ">
				<form:form modelAttribute="salesExecBeatCustomer" method="post"
					action="/crm/web/salesExecWeb/scheduleVisit" name="myForm" id="myForm">
					<fieldset>
						<legend>Sales Executive Visit Schedule of today</legend>
						<div class="form-group required">
							<label class='control-label'>Visit Date</label>
							<form:input id="dp" name="visitDate" cssClass="dp form-control"
								path="visitDate" />
						</div>
						<div class="form-group required">
							<label class='control-label'>Sales Executive</label>
							<form:select path="salesExecutiveID" cssClass="form-control"
								id="sales_exec">
								<form:option value="-1" label="--- Select Sales Executive---" />
								<c:forEach var="salesExec" items="${salesExecs}">
									<form:option value="${ salesExec.userID }"
										label="${ salesExec.firstName } ${ salesExec.lastName }"
										id="id_trial" />
								</c:forEach>
							</form:select>
						</div>
							
						<div class="form-group required">
							<label class='control-label'>Beats</label>
							<form:select path="beatID" cssClass="form-control" id="beats">
								<option value="-1" label="--- Select Beat---" />
							</form:select>
						</div>
						<div class="form-group required">
							<label class='control-label'>Customers</label>
							<div style="width: 200px; min-height: 2px; max-height: 100px; overflow-y: auto;" id="checks">
							</div>
						</div>
					</fieldset>
					<div class="form_submit">
						<input type="button" name="btn" value="Submit" id="submitBtn" data-toggle="modal" data-target="#confirm-submit" class="btn btn-primary" />
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
			$(document).ready(function() {
				$('#sales_exec').change(function() {
					$.ajax({
							type : "GET",
							url : "/crm/rest/salesExecReST/"+$('#sales_exec').val(),
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
			
			
			$(document).ready(function() {
				$('#beats').change(function() {
					$.ajax({
							type : "GET",
							url : "/crm/rest/beatReST/"+$('#beats').val(),
							dataType : "json",
							success : function(data) {
								$('#checks').empty();
								$.each(data,function(i,obj) {
									var div_data = "<input name=customerIDs id=customerIDs type=checkbox value="+obj.customerID+" checked>"+obj.customerName+"<input type=hidden id="+obj.customerID+" value="+ obj.customerName +"><br>";
									$(div_data).appendTo('#checks');
								});
							}
						});
					});
				});
			
			$('#submitBtn').click(function() {
			     /* when the button in the form, display the entered values in the modal */
			     //Sales Executive
			     var salesEXId = document.getElementById("sales_exec");
				 var salesExecName = salesEXId.options[salesEXId.selectedIndex].text;
			     $('#salesEX').text(salesExecName);
			     
			     //Beat
			     var beatId = document.getElementById("beats");
				 var beatName = beatId.options[beatId.selectedIndex].text;
			     $('#beat').text(beatName);
			     
			     //Customers
			     $('#customers').empty();
			     var checkedValues = $('input:checkbox:checked').map(function() {
			    	    return this.value;
			    	}).get();
			  	 $.each( checkedValues, function( key, value ) {
			    	 var listItem = "<li>"+$("#"+value).val()+"</li>";
			    	 $(listItem).appendTo('#customers');
			     });
			  	 
			  	 //Visit Date
			  	 $('#visiDate').text($("#dp").val());
			});
			
			$('#dp').datepicker({format: 'dd-mm-yyyy'});
			
			$(document).ready(function() {
	       		$("#dp").prop('required',true);
			});
			
			$(document).ready(function() {
	       		$("#sales_exec").prop('required',true);
			});
			
			$(document).ready(function() {
	       		$("#beats").prop('required',true);
			});
			
			$(document).ready(function() {
	       		$("#checks").prop('required',true);
			});
			
		</script>

	<div class="modal fade" id="confirm-submit" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header"><b>Confirm Sales Executive Visit</b></div>
				<div class="modal-body">
					Are you sure you want to schedule the visit for sales executive ?
					<br>
					<br>
					<div>
						<label>Sales Executive Name : </label> <span id="salesEX"></span>
					</div>
					<div>
						<label>Beat Name : </label> <span id="beat"></span>
					</div>
					<div>
						<label> Customers : </label>
						<ul id="customers"></ul>
					</div>
						<label> Visit Date : </label> <span id="visiDate"></span>
					<div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
					<!-- a href="#" id="submit" class="btn btn-success success">Submit</a-->
					<button type="submit" id="modalSubmit" class="btn btn-primary">Submit</button>
					
					<script type="text/javascript">
					$('#modalSubmit').click(function(){
					     /* when the submit button in the modal is clicked, submit the form */
					   $('#myForm').submit();
					});
					</script>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
