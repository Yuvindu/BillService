$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateBillForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hiduidSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "BillService",  
			type : type,  
			data : $("#formBill").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onBillSaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onBillSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divBillGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidInnovatorIDSave").val("");  
	$("#formInnovator")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hiduidSave").val($(this).closest("tr").find('#hiduidUpdate').val());     
	$("#uid").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#amount").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#paid").val($(this).closest("tr").find('td:eq(2)').text());  
	$("#created_at").val($(this).closest("tr").find('td:eq(6)').text());      
}); 


//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "BillService",   
		type : "DELETE",   
		data : "uid=" + $(this).data("iid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onBillDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onBillDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divBillGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 
// CLIENT-MODEL========================================================================= 
function validateBillForm() 
{  
	// UID  
	if ($("#uid").val().trim() == "")  
	{   
		return "Insert payee_uid.";  
	} 

	// Amount------------------------  
	if ($("#amount").val().trim() == "")  
	{   
		return "Insert due amount.";  
	} 
	
	

	//address-------------------------------
	if ($("#paid").val().trim() == "")  
	{   
		return "Insert paid amount.";  
	} 
	
	
	
	//created_at-------------------------------
	if ($("#created_at").val().trim() == "")  
	{   
		return "Insert created_at.";  
	} 
	
	return true; 
}