
/**
* @brief document onReady event handling
*/
$(document).ready(()=>{
  $("#btn").click(send_user_text);
  $("#ss").click(enable_service);
  $("#next").click(nextItem);
});

function send_user_text() {
  lcd_write($("#line1").val(), $("#line2").val());
}

function enable_service() {
  $("#ss").unbind();
  $("#ss").click(disable_service);
  $("#ss").html('Stop App');
  lcd_service(1);
  startTimer();
}

function disable_service() {
  $("#ss").unbind();
  $("#ss").click(enable_service);
  $("#ss").html('Start App');
  lcd_service(0);
  stopTimer();
}

var timer;

/**
* @brief Start request timer
*/
function startTimer(){
  timer = setInterval(updateDisplay, 1000);
}

/**
* @brief Stop request timer
*/
function stopTimer(){
  clearInterval(timer);
}

function updateDisplay() {
	const url = 'http://' + window.location.hostname + '/lcd_read.php'
	$.ajax(url, 
	{
		type: 'GET',
		dataType: 'text',
    error: requestError,
		success: (response, status, xhr) => {	
      var lines = response.split("\n");
      $("#line1").val(lines[0].replace(/[^\x00-\x7F]/g, "?"));
      $("#line2").val(lines[1].replace(/[^\x00-\x7F]/g, "?"));
		}
	});
}

function nextItem() {
	const url = 'http://' + window.location.hostname + '/pushbtn.php'
	$.ajax(url, 
	{
		type: 'GET',
		dataType: 'text',
    error: requestError
	});
}

function lcd_write(l1, l2) {
	const url = 'http://' + window.location.hostname + '/lcd_write.php'
	$.ajax(url + "?line1=" + l1 + "&line2=" + l2, 
	{
		type: 'GET',
		dataType: 'text',
    error: requestError,
		success: (response, status, xhr) => {	
			$("#response").html(response);
		}
	});
}

function lcd_service(en) {
	const url = 'http://' + window.location.hostname + '/lcd_service.php'
	$.ajax(url + "?enable=" + en, 
	{
		type: 'GET',
		dataType: 'text',
    error: requestError,
		success: (response, status, xhr) => {	
			$("#response").html(response);
		}
	});
}

/**
* @brief GET request error handling
* 		   https://restfulapi.net/http-status-codes/
*/
function requestError(jqXhr, textStatus, errorMessage) { 
	if(errorMessage == "") errorMessage = "Failed to fetch";
	var errMsg = '<font color="red">Error: ' + errorMessage + ' (' + jqXhr.status + ')</font>'
	$("#response").html(errMsg);
}