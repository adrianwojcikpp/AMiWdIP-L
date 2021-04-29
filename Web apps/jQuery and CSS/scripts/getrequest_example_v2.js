const initUrlJson = 'http://localhost/server mock/chartdata.json'
const initUrlPHP  = 'http://localhost/server mock/chartdata.php'
const requestMethod = [ajaxText, ajaxJSON, getPHP];

/**
* @brief document onReady event handling
*/
$(document).ready(()=>{
	/// url input 
	$("#url").val(initUrlJson);
	/**
	* @brief button onClick event handling
	*/
	$("#btn").click(requestMethod[0]);
	$("#request").change(()=>{
		var requestMethodName = $("#request").children("option:selected").val();
		$("#btn").unbind();
		$("#btn").click(requestMethod[requestMethodName]);
		
		if(requestMethodName == 2) $("#url").val(initUrlPHP)
		else $("#url").val(initUrlJson)
	});
});

function ajaxText() {
	/// Send request
	$.ajax($("#url").val(), 
	{
		type: 'GET',
		dataType: 'text',
		/// success callback
		success: (responseText, status, xhr) => {	
			$("#response").val(responseText);
			var jsonObj = $.parseJSON(responseText);
			$("#json").val(jsonObj.data);
		},
		/// error callback
		error: requestError
	});
}

function ajaxJSON() {
	/// Send request
	$.ajax($("#url").val(), 
	{
		type: 'GET',
		dataType: 'json',
		/// success callback
		success: function(responseJSON, status, xhr) {	
			$("#response").val(JSON.stringify(responseJSON));
			$("#json").val(responseJSON.data);
		},
		/// error callback
		error: requestError
	});
}

function getPHP() {
	/// GET arguments
	var getData = { filename: "chartdata" };
	
	$.get($("#url").val(), getData, 
		/// success callback
		function(responseJSON,status,xhr) {
			$("#response").val(JSON.stringify(responseJSON));
			$("#json").val(responseJSON.data);
		},
		'json').fail(requestError); /// error callback
}

/**
* @brief GET request error handling
* 		 https://restfulapi.net/http-status-codes/
*/
function requestError(jqXhr, textStatus, errorMessage) { 
	if(errorMessage == "") errorMessage = "Failed to fetch";
	var errMsg = '<font color="red">Error: ' + errorMessage + ' (' + jqXhr.status + ')</font>'
	$("#response").html(errMsg);
	$("#json").val('');
}