const ServerIoT = new XMLHttpRequest();
const initUrl = 'http://192.168.0.10/server/chartdata.json'

var errorCode;

/**
* @brief XMLHttpRequest Load event handling
*/
ServerIoT.onload = ()=>{
	errorCode = ServerIoT.status.toString();
	if(errorCode[0] == '2') { /// 2xx: Success
		document.getElementById("response").value = ServerIoT.responseText;
		var jsonObj = JSON.parse(ServerIoT.responseText);
		document.getElementById("json").value = jsonObj.data;
	} else {
		requestError(errorCode, ServerIoT.statusText);
	}
}

/**
* @brief XMLHttpRequest Error event handling: network-level error
*/
ServerIoT.onerror = ()=>{
	requestError(ServerIoT.status, "Failed to fetch");
}

/**
* @brief button Click event handling
*/
function getRequest() {
	var url = document.getElementById("url").value;
	ServerIoT.open("GET", url);
	/// do not use cache: always get response from server
	ServerIoT.setRequestHeader("Cache-Control", "max-age=0");
	ServerIoT.send();
}

/**
* @brief GET request error handling
* 		 https://restfulapi.net/http-status-codes/
*/
function requestError(code, errtext) {
	errorCode = code;
	var errMsg = '<font color="red">Error: ' + errtext + ' (' + errorCode + ')</font>'
	document.getElementById("response").innerHTML = errMsg;
	document.getElementById("json").value = '';
}

/**
* @brief window Load event handling
*/
window.onload = ()=>{
	document.getElementById("url").value = initUrl;
	document.getElementById("btn").onclick = getRequest;
}