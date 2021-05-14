$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertSuccess").hide();

});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateOrdForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------

	var type = ($("#hidOrdIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "OrdersAPI",
		type : type,
		data : $("#formOrd").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onOrdSaveComplete(response.responseText, status);
		}
	});
});

function onOrdSaveComplete(response, status) {
	
	
	if (status == "success") {
		var resultSet = JSON.parse(response);

    if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divOrdGrid").html(resultSet.data);

		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();

	} 

	else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidOrdIDSave").val("");
	$("#formOrd")[0].reset();
}

// UPDATE==========================================
$(document).on("click",".btnUpdate",function(event) {
			$("#hidOrdIDSave").val($(this).data("order_id"));
			$("#customerID").val($(this).closest("tr").find('td:eq(0)').text());
			$("#customerName").val($(this).closest("tr").find('td:eq(1)').text());
		    $("#productID").val($(this).closest("tr").find('td:eq(2)').text());
		    $("#date").val($(this).closest("tr").find('td:eq(3)').text());
			
			
		});

// REMOVE==========================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "OrdersAPI",
		type : "DELETE",
		data : "order_id=" + $(this).data("order_id"),
		dataType : "text",
		complete : function(response, status) {
			onOrdDeleteComplete(response.responseText, status);
		}
	});
});

function onOrdDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divOrdGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENTMODEL=========================================================================
/*function validateOrdForm() {
	// Date
	if ($("#date").val().trim() == "") {
		return "Select a date.";
	}
	

	// CustomerID-------------------------------
	if ($("#cid").val().trim() == "") {
		return "Insert your customerID.";
	}

	
	// Project ID-------------------------------
	if ($("#pid").val().trim() == "") {
		return "Insert your projectID.";
	}
	
	

	return true;
}*/


