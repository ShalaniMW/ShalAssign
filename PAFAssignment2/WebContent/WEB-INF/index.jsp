<%@page import="com.Orders"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Orders Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Orders.js"></script>


</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Orders Management</h1>
				<form id="formOrd" name="formOrd">
					
						<br>CustomerID: <input id="cid" name="cid" type="text"
					class="form-control form-control-sm"> <br> 
						
						<br>Customer Name: <input id="cname" name="cname" type="text"
					class="form-control form-control-sm"> <br> 
						
					 
						<br>ProductID: <input id="pid" name="pid" type="text"
					class="form-control form-control-sm"> <br> 
						
						<br>Date: <input id="date" name="date" type="text"
					class="form-control form-control-sm"> <br> 
					
					<input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary">
						
					 <input type="hidden" id="hidOrdIDSave" name="hidOrdIDSave" value="">
					 
				</form>
				
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divOrdGrid">
				
					<%
					Orders OrdObj = new Orders();
						out.print(OrdObj.ReadOrders());  
					%>
					
				</div>
			</div>
		</div>
	</div>

</body>
</html>