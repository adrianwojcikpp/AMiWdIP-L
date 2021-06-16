/**
* @brief document onReady event handling
*/
$(document).ready(()=>{
  $("#send").click(writeLines);
  $("#app").click(startApp);
  $("#next").click(nextItem);
});

/**
* @brief On click event handler for 'app' button
*        Starting LCD embedded application
*/
function startApp() {
  $("#app").unbind();
  $("#app").click(stopApp);
  $("#app").html('Stop App');
  serviceControl(1);
  startDisplayTimer();
}

/**
* @brief On click event handler for 'app' button
*        Stopping LCD embedded application
*/
function stopApp() {
  $("#app").unbind();
  $("#app").click(startApp);
  $("#app").html('Start App');
  serviceControl(0);
  stopDisplayTimer();
}

var displayTimer //< Display update timer 
const displayTimerPeriod = 1000;   //< ms 

/**
* @brief Start display update timer
*/
function startDisplayTimer(){
  displayTimer = setInterval(readLines, displayTimerPeriod);
}

/**
* @brief Stop display update timer
*/
function stopDisplayTimer(){
  clearInterval(displayTimer);
}

/**
* @brief Reading display lines form web server
*/
function readLines() {
  WebServerRequest(WebServerAPI.LcdRead, [], (response) => {	
    var lines = response.split("\n");
    $("#line1").val(lines[0].replace(/[^\x00-\x7F]/g, "?"));
    $("#line2").val(lines[1].replace(/[^\x00-\x7F]/g, "?"));
  });
}

/**
* @brief Selecting next item on menu list via web server
*/
function nextItem() {
  WebServerRequest(WebServerAPI.LcdNext, [], () => {});
}

/**
* @brief Writing display lines via web server
*/
function writeLines() {
  WebServerRequest(WebServerAPI.LcdWrite, [
    $("#line1").val(), $("#line2").val()
  ],(response) => { 
    $("#response").html(response); 
  });
}

/**
* @brief LCD embedded application control
*/
function serviceControl(en) {
  WebServerRequest(WebServerAPI.LcdService, [en],(response) => { 
    $("#response").html(response); 
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