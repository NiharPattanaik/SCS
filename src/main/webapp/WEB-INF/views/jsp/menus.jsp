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
					<li><a href="<%=request.getContextPath()%>/web/dashboardWeb/dashboard">Dashboard</a></li>
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" id="reportmenu">Masters<span class="caret"></span></a>
        				<ul class="dropdown-menu" id="reportmenus">
        					<% if(resourcePermIDs.contains(ResourcePermissionEnum.CUSTOMER_LIST.getResourcePermissionID())) { %>
								<li><a href="<%=request.getContextPath()%>/web/customerWeb/list">Customers</a></li>
							<% } %>	
					
							<% if(resourcePermIDs.contains(ResourcePermissionEnum.RESELLER_LIST.getResourcePermissionID())) { %>
								<li><a href="<%=request.getContextPath()%>/web/resellerWeb/list">Resellers</a></li>
							<% } %>	
							
							<% if(resourcePermIDs.contains(ResourcePermissionEnum.SUPPLIER_LIST.getResourcePermissionID())) { %>
								<li><a href="<%=request.getContextPath()%>/web/supplierWeb/list">Suppliers</a></li>
							<% } %>	
							<li><a href="<%=request.getContextPath()%>/web/manufacturerWeb/list">Manufacturers</a></li>
							<% if(resourcePermIDs.contains(ResourcePermissionEnum.AREA_LIST.getResourcePermissionID())) { %>
								<li><a href="<%=request.getContextPath()%>/web/areaWeb/list">Areas</a></li>
							<% } %>	
							
							<% if(resourcePermIDs.contains(ResourcePermissionEnum.BEAT_LIST.getResourcePermissionID())) { %>
								<li><a href="<%=request.getContextPath()%>/web/beatWeb/list">Beats</a></li>
							<% } %>
						</ul>
        			</li>	
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" id="reportmenu">Order Management<span class="caret"></span></a>
        				<ul class="dropdown-menu" id="reportmenus">
        					<% if(resourcePermIDs.contains(ResourcePermissionEnum.ORDER_LIST.getResourcePermissionID())) { %>
        						<li><a href="<%=request.getContextPath()%>/web/orderWeb/list">Orders</a></li>
        					<% } %>		
        					<% if(resourcePermIDs.contains(ResourcePermissionEnum.ORDER_VIEW_SCHEDULED_ORDER_BOOKINGS.getResourcePermissionID())) { %>
        						<li><a href="<%=request.getContextPath()%>/web/orderWeb/scheduledOrderBookings">Schedule Order Booking</a></li>
        					<% } %>	
        					<% if(resourcePermIDs.contains(ResourcePermissionEnum.ORDER_SCHEDULE_DELIVERY_BOOKING.getResourcePermissionID())) { %>
        						<li><a href="<%=request.getContextPath()%>/web/deliveryExecWeb/scheduledDeliveryBookings">Delivery Booking</a></li>
        					<% } %>		
        					<!-- li><a href="#">Payment Booking</a></li-->
					    </ul>
        			</li>	
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" id="reportmenu">Reports<span class="caret"></span></a>
        				<ul class="dropdown-menu" id="reportmenus">
        					<% if(resourcePermIDs.contains(ResourcePermissionEnum.OTP_LIST.getResourcePermissionID())) { %>
					          	<li><a href="<%=request.getContextPath()%>/web/otpWeb/report">OTP Report</a></li>
					         <% } %>	
					         <li><a href="<%=request.getContextPath()%>/web/orderWeb/orderScheduleReport">Order Schedule Report</a></li>
        				</ul>
        			</li>
					<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#" id="adminmenu">Administration<span class="caret"></span></a>
        					<ul class="dropdown-menu" id="adminmenus">
		      					<% if(resourcePermIDs.contains(ResourcePermissionEnum.RESELLER_READ.getResourcePermissionID())) { %>
		      			    		<li><a href="<%=request.getContextPath()%>/web/resellerWeb/view">Reseller Profile</a></li>
					          	<% } %>	
				          	
					          	<% if(resourcePermIDs.contains(ResourcePermissionEnum.USER_LIST.getResourcePermissionID())) { %>
					          		<li><a href="<%=request.getContextPath()%>/web/userWeb/list">Users</a></li>
								<% } %>	
							
								<% if(resourcePermIDs.contains(ResourcePermissionEnum.ROLE_LIST.getResourcePermissionID())) { %>
									<li><a href="<%=request.getContextPath()%>/web/role/list">Roles</a></li>
								<% } %>	
							
								<% if(resourcePermIDs.contains(ResourcePermissionEnum.USER_VIEW_ASSIGNED_BEATS.getResourcePermissionID())) { %>
									<li><a href="<%=request.getContextPath()%>/web/salesExecWeb/beatlist">Sales Executive-Beats</a></li>
					          	<% } %>	
					          	
					          	<% if(resourcePermIDs.contains(ResourcePermissionEnum.USER_VIEW_ASSIGNED_BEATS.getResourcePermissionID())) { %>
					          		<li><a href="<%=request.getContextPath()%>/web/deliveryExecWeb/beatlist">Delivery Executive-Beats</a></li>
				          		<% } %>	
				          		
					          	<% if(resourcePermIDs.contains(ResourcePermissionEnum.BEAT_VIEW_ASSOCIATED_CUSTOMERS.getResourcePermissionID())) { %>
					          		<li><a href="<%=request.getContextPath()%>/web/beatWeb/beat-customers/list">Customer - Beats</a></li>
					          	<% } %>	
					          	<li><a href="<%=request.getContextPath()%>/web/supplierWeb/supp-manufacturer/list">Supplier - Manufacturer</a></li>
					          	<li><a href="<%=request.getContextPath()%>/web/supplierWeb/supp-salesexecs/list">Supplier - Sales Executives</a></li>
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
		
		$( document ).ready(function() {
			if ($('#reportmenus li').length == 0){
				$('#reportmenu').hide();
			}
		});
	</script>