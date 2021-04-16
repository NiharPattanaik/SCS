<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
	<title>Create User</title>
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
		<!-- Links -->
		<%@ include file="menus.jsp" %>
		<div class="row top-height">
			<div class="col-md-8 ">
				<h2>Add New User</h2>
				<form:form modelAttribute="user" method="post"
					action="/crm/web/userWeb/save">
					<fieldset>
						<legend>User Details</legend>
						<div class="form-group required" >
							<label class='control-label'>First Name</label>
							<form:input name="firstName" cssClass="form-control" path="firstName" required="required"/>
						</div>
						
						<div class="form-group">
							<label>Last Name</label>
							<form:input name="lastName" cssClass="form-control"
								path="lastName" />
						</div>
						<div class="form-group">
							<label>Description</label>
							<form:input name="description" cssClass="form-control"
								path="description" />
						</div>
						<div class="form-group" id="emailID">
							<label id="emailIDLbl">Email ID</label>
							<form:input name="emailID" cssClass="form-control"
								path="emailID" id="emailIDInput"/>
						</div>
						<div class="form-group" id="mobile">
							<label id="mobileLbl">Mobile Number</label>
							<form:input name="mobileNo" cssClass="form-control" id="mobileInput"
								path="mobileNo" />
						</div>
					</fieldset>
					
					<fieldset>
						<legend>Role Details</legend>
						<div class="form-group required">
							<label class='control-label'>Roles</label>
							<form:select path="roleIDs" cssClass="form-control" multiple="true" id="roles">
								<form:option value="-1" label="--- Select ---" />
								<form:options items="${roles}" itemValue="roleID"
									itemLabel="roleName" required="required"/>
							</form:select>
						</div>
					</fieldset>
					
					<div id="login">
						<fieldset>
							<legend>Login Details</legend>
							<div class="form-group required">
								<label class='control-label'>User Name</label>
								<form:input name="userName" cssClass="form-control" path="userName" required="required"/>
							</div>
							<div class="form-group required" id="password">
								<label id="passwordLbl">Password</label>
								<form:input name="password" cssClass="form-control"
									path="password" id="passwordInput"/>
							</div>
							<div class="form-group required">
								<label class='control-label'>Select how to get password.</label>
								<br>
								<form:radiobutton name="passwordMedium" id="passwordMedium1" path="passwordMedium" value="1"/> &nbsp; Allow user to enter password.<br>
								<form:radiobutton name="passwordMedium" id="passwordMedium2" path="passwordMedium" value="2"/> &nbsp; Display auto generated password.<br>
								<form:radiobutton name="passwordMedium" id="passwordMedium3" path="passwordMedium" value="3"/> &nbsp; Send auto generated password by email.<br>
								<form:radiobutton name="passwordMedium" id="passwordMedium4" path="passwordMedium" value="4"/> &nbsp; Send auto generated by SMS.<br>
							</div>
						</fieldset>
					</div>
					<form:hidden path="statusID" name="statusID" value="2"/>
					<div class="form_submit">
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	
		$(document).ready(function() {
			$('#password').hide();
		});
		
		$(document).ready(function() {
       		$("#firstName").prop('required',true);
		});
		
		$(document).ready(function() {
       		$("#roles").prop('required',true);
		});
		
		$(document).ready(function() {
       		$("#userName").prop('required',true);
		});
		
		
		$('input[type="radio"]').change(function() {
			var value = $(this).attr("value");
			if(value == "1"){
				$('#password').show();
				$('#emailID').removeClass("required");
				$('#emailIDLbl').removeClass("control-label");
				$("#emailIDInput").prop('required',false);
				$('#mobile').removeClass("required");
				$('#mobileLbl').removeClass("control-label");
				$("#mobileInput").prop('required',false);
				$('#password').addClass("required");
				$('#passwordLbl').addClass("control-label");
				$("#passwordInput").prop('required',true);
			}else if(value == "2"){
				$('#password').hide();
				$('#emailID').removeClass("required");
				$('#emailIDLbl').removeClass("control-label");
				$("#emailIDInput").prop('required',false);
				$('#mobile').removeClass("required");
				$('#mobileLbl').removeClass("control-label");
				$("#mobileInput").prop('required',false);
				$('#password').removeClass("required");
				$('#passwordLbl').removeClass("control-label");
				$("#passwordInput").prop('required',false);
			}else if(value == "3"){
				$('#password').hide();
				$('#emailID').addClass("required");
				$('#emailIDLbl').addClass("control-label");
				$("#emailIDInput").prop('required',true);
				$('#mobile').removeClass("required");
				$('#mobileLbl').removeClass("control-label");
				$("#mobileInput").prop('required',false);
				$('#password').removeClass("required");
				$('#passwordLbl').removeClass("control-label");
				$("#passwordInput").prop('required',false);
			}else if(value == "4"){
				$('#password').hide();
				$('#emailID').removeClass("required");
				$('#emailIDLbl').removeClass("control-label");
				$("#emailIDInput").prop('required',false);
				$('#mobile').addClass("required");
				$('#mobileLbl').addClass("control-label");
				$("#mobileInput").prop('required',true);
				$('#password').removeClass("required");
				$('#passwordLbl').removeClass("control-label");
				$("#passwordInput").prop('required',false);
			}
		});
		
	</script>
</body>

</html>
