<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.sales.crm.model.Beat"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
	<title>Assign Beats To Customer</title>
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
				<form:form modelAttribute="beat" method="post"
					action="/crm/web/beatWeb/assignBeatToCustomers">
					<fieldset>
						<legend>Assign Beats to Customer</legend>
						<div class="form-group required">
								<label class='control-label'>Beat</label>
								<form:select path="beatID" cssClass="form-control" id="beats">
									<form:option value="-1" label="--- Select ---" required="required"/>
									<c:forEach var="cbeat" items="${beats}">
										<% 
											if(!((Beat)pageContext.getAttribute("cbeat") != null 
												&& ((Beat)pageContext.getAttribute("cbeat")).getCustomers() != null 
													&& ((Beat)pageContext.getAttribute("cbeat")).getCustomers().size() > 0)){
										%>
											<form:option value="${ cbeat.beatID }" label="${ cbeat.name }" required="required"/>
										<% 
											}
										%>
											
									</c:forEach>
								</form:select>
							</div>
							<div class="form-group required">
								<label class='control-label'>Customers</label>
								<form:select path="customerIDs" cssClass="form-control" multiple="true" id="customers">
									<form:option value="-1" label="--- Select ---" />
									<form:options items="${customers}" itemValue="customerID"
										itemLabel="customerName" required="required"/>
								</form:select>
							</div>
					</fieldset>
					<div class="form_submit">
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#beats").prop('required',true);
	});
	
	$(document).ready(function() {
		$("#customers").prop('required',true);
	});
</script>
</html>
