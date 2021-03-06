<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">

<head>
	<title>Create Beat</title>
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
		<%@ include file="menus.jsp" %>
		<div class="row top-height">
			<div class="col-md-8 ">
				<h2>Add New Beat</h2>
				<form:form modelAttribute="beat" method="post"
					action="${contextPath}/web/beatWeb/save">
					<fieldset>
						<legend>Beat Details</legend>
						<div class="form-group required">
							<label class='control-label'>Beat Name</label>
							<form:input id="name" name="name" cssClass="form-control" path="name" required="required"/>
							<label id="beatMsg" style="color:red; font-style: italic; font-weight: normal;">Beat with same name already exists.</label>
						</div>
						<div class="form-group">
							<label>Description</label>
							<form:input name="description" cssClass="form-control"
								path="description" />
						</div>
						<div class="form-group">
							<label>Coverage Schedule</label>
							<form:select path="coverageSchedule" cssClass="form-control">
								<form:option value="-1" label="--- Select ---" />
								<form:option value="Daily" label="Daily" />
								<form:option value="Weekly" label="Weekly" />
								<form:option value="Fortnightly" label="Fortnightly" />
								<form:option value="Monthly" label="Monthly" />
								<form:option value="Other" label="Other" />
							</form:select>
						</div>
						<div class="form-group">
							<label>Distance</label>
							<form:input name="distance" cssClass="form-control" path="distance" />
						</div>
					</fieldset>
					<fieldset>
						<legend>Areas Covered</legend>
						<div class="form-group">
							<label>Areas</label>
							<c:choose>
								<c:when test = "${not empty areas}">
									<form:select path="areaIDs" cssClass="form-control" multiple="true">
										<form:options items="${areas}" itemValue="areaID"
											itemLabel="name" />
									</form:select>
								</c:when>
								<c:otherwise>
										<form:select path="areaIDs" cssClass="form-control" multiple="true">
											<option value=>No Areas found to be mapped</option>
										</form:select>
								</c:otherwise>				
							</c:choose>	
						</div>
					</fieldset>
					<div class="form_submit">
						<button type="button" class="btn btn-primary" id="cancelbtn" onclick="window.history.back(); return false;"">Cancel</button>
						<button type="button" class="btn btn-primary" id="resetBtn" onclick="location.reload();">Reset</button>
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
	   		$("#name").prop('required',true);
	   		$("#manufacturer").prop('required',true);
	   	    $("#beatMsg").hide();
	   	    
	   	 $('input[id=name]').blur(function() {
	           if("${beatNames}".toLowerCase().includes($(this).val().toLowerCase())){
	        	   $("#beatMsg").show();
	           }else{
	        	   $("#beatMsg").hide();
	           }
	      });
		});
	</script>
</body>

</html>
