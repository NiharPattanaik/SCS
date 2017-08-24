<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.sales.crm.model.Beat"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
	<title>Assign Sales Executive To Supplier</title>
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

fieldset {
	border: 1px solid grey;
	padding: 10px;
	border-radius: 5px;
}

legend {
	width: auto !important;
	border-bottom: 0px !important;
}

.side_nav_btns {
	margin-top: 10px;
}

}
.top-height {
	margin-top: 2%;
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
		<%@ include file="menus.jsp" %>
		<div class="row top-height">
			<div class="col-md-8 ">
				<form:form modelAttribute="supplier" method="post"
					action="/crm/web/supplierWeb/assignSalesExecutive">
					<fieldset>
						<legend>Assign Sales Executive to Supplier</legend>
							<div class="form-group required">
								<label class='control-label'>Supplier</label>
								<form:select path="supplierID" cssClass="form-control" id="suppliers">
									<form:option value="-1" label="--- Select ---" required="required"/>
									<c:forEach var="csupplier" items="${suppliers}">
										<form:option value="${ csupplier.supplierID }" label="${ csupplier.name }" required="required"/>
									</c:forEach>
								</form:select>
							</div>
							<div class="form-group required">
								<label class='control-label'>Sales Executives</label>
								<form:select path="salesExecsIDs" cssClass="form-control" id="salesExecs"
									multiple="true">
									<form:option value="-1" label="--- Select ---"/>
								</form:select>
							</div>
					</fieldset>
					<div class="form_submit">
						<button type="submit" class="btn btn-primary" id="submitbtn">Submit</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#suppliers").prop('required',true);
		
		$('#suppliers').change(function() {
			if($('#suppliers').val() != -1){
				$.ajax({
						type : "GET",
						url : "/crm/rest/salesExecReST/salesExecNotMappedToSupp/"+$('#suppliers').val(),
						dataType : "json",
						success : function(data) {
							$('#salesExecs').empty();
							$.each(data,function(i,obj) {
								var div_data = "<option value="+obj.userID+">"+ obj.name+ "</option>";
								$(div_data).appendTo('#salesExecs');
						});
					}
				});
			}
		});
		$('#submitbtn').click(function(){
		     /* when the submit button in the modal is clicked, submit the form */
		   $('#myForm').submit();
		});
		
	});
	
</script>
</html>
