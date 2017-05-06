<%@page import="com.sales.crm.model.ResourcePermissionEnum"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.sales.crm.model.User"%>

<%
	List<Integer> resourcePermIDs = (ArrayList)session.getAttribute("resourcePermIDs");
%>
<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#"></a>
				</div>
				<ul class="nav navbar-nav">
					<% if(resourcePermIDs.contains(ResourcePermissionEnum.CUSTOMER_LIST.getResourcePermissionID())) { %>
						<li><a href="<%=request.getContextPath()%>/web/customerWeb/list/<%=Integer.valueOf(String.valueOf(session.getAttribute("resellerID")))%>">Customers</a></li>
					<% } %>	
					
					<% if(resourcePermIDs.contains(ResourcePermissionEnum.SUPPLIER_LIST.getResourcePermissionID())) { %>
						<li><a href="<%=request.getContextPath()%>/web/supplierWeb/list/<%=Integer.valueOf(String.valueOf(session.getAttribute("resellerID")))%>">Suppliers</a></li>
					<% } %>	
					
					<% if(resourcePermIDs.contains(ResourcePermissionEnum.AREA_LIST.getResourcePermissionID())) { %>
						<li><a href="<%=request.getContextPath()%>/web/areaWeb/list/<%=Integer.valueOf(String.valueOf(session.getAttribute("resellerID")))%>">Areas</a></li>
					<% } %>	
					
					<% if(resourcePermIDs.contains(ResourcePermissionEnum.BEAT_LIST.getResourcePermissionID())) { %>
						<li><a href="<%=request.getContextPath()%>/web/beatWeb/list/<%=Integer.valueOf(String.valueOf(session.getAttribute("resellerID")))%>">Beats</a></li>
					<% } %>	
					<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#" id="adminmenu">Administration<span class="caret"></span></a>
        					<ul class="dropdown-menu" id="adminmenus">
		      					<% if(resourcePermIDs.contains(ResourcePermissionEnum.RESELLER_READ.getResourcePermissionID())) { %>
		      			    		<li><a href="<%=request.getContextPath()%>/web/resellerWeb/<%=Integer.valueOf(String.valueOf(session.getAttribute("resellerID")))%>">Reseller Profile</a></li>
					          	<% } %>	
				          	
					          	<% if(resourcePermIDs.contains(ResourcePermissionEnum.CUSTOMER_LIST.getResourcePermissionID())) { %>
					          		<li><a href="<%=request.getContextPath()%>/web/userWeb/list">Users</a></li>
								<% } %>	
							
								<% if(resourcePermIDs.contains(ResourcePermissionEnum.ROLE_LIST.getResourcePermissionID())) { %>
									<li><a href="<%=request.getContextPath()%>/web/role/list">Roles</a></li>
								<% } %>	
							
								<% if(resourcePermIDs.contains(ResourcePermissionEnum.USER_BEAT_ASSIGNMENT.getResourcePermissionID())) { %>
									<li><a href="<%=request.getContextPath()%>/web/salesExecWeb/beatlist/<%=Integer.valueOf(String.valueOf(session.getAttribute("resellerID")))%>">Sales Executive-Beats</a></li>
					          	<% } %>	
				          	
					          	<% if(resourcePermIDs.contains(ResourcePermissionEnum.BEAT_CUSTOMER_ASSIGNMENT.getResourcePermissionID())) { %>
					          		<li><a href="<%=request.getContextPath()%>/web/beatWeb/beat-customers/list">Beat - Customer</a></li>
					          	<% } %>	
				          	
					          	<% if(resourcePermIDs.contains(ResourcePermissionEnum.USER_SCHEDULE_VISIT.getResourcePermissionID())) { %>
					          		<li><a href="<%=request.getContextPath()%>/web/salesExecWeb/salesExecBeatsCustList">Scheduled Visit</a></li>
					          	<% } %>	
				          	
					          	<% if(resourcePermIDs.contains(ResourcePermissionEnum.OTP_LIST.getResourcePermissionID())) { %>
					          		<li><a href="<%=request.getContextPath()%>/web/otpWeb/report">OTP Report</a></li>
					          	<% } %>		
	      				</ul>
      				</li>
				</ul>		
				<ul class="nav navbar-nav navbar-right ">
					<li class="dropdown">
        				<a class="dropdown-toggle" data-toggle="dropdown" href="#"><%= (String)session.getAttribute("userFullName") %> <span class="glyphicon glyphicon-user"></span></a>
	      				<ul class="dropdown-menu">
				        	<li><a href="<%=request.getContextPath()%>/web/userWeb/account/<%= ((User)session.getAttribute("user")).getUserID() %>">Account Details</a></li>
				          	<li><a href="<%=request.getContextPath()%>/logout">logout</a></li>
	      				</ul>
      				</li>	
					
				</ul>
			</div>
		</nav>
	<script type="text/javascript">
	$( document ).ready(function() {
		if ($('#adminmenus li').length == 0){
			$('#adminmenu').hide();
		}
	});
	</script>