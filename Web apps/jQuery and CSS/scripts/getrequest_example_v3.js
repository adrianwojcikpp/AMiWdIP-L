const initUrl = 'http://localhost/server mock/chartdata.json'

var errorCode;

/**
* @brief button Click event handling
*/
function getRequest() {
	var url = document.getElementById("url").value;
	
	fetch(url)
	.then((response) => { 
	
		if (response.ok) 
			return response.json();
		else 
			return Promise.reject(response);
		
	})
	.then((responseJSON) => {
		
		document.getElementById("response").value = JSON.stringify(responseJSON);
		document.getElementById("json").value = responseJSON.data;
		
	})
	.catch((error) => {
		
		var errMsg = '<font color="red">Error: ';
		if(error.status != null)
			errMsg += error.statusText + ' (' + error.status + ')</font>';
		else
			errMsg += error.message + '</font>';
		document.getElementById("response").innerHTML = errMsg;
		document.getElementById("json").value = '';
		
	});
}

/**
* @brief window Load event handling
*/
window.onload = ()=>{
	document.getElementById("url").value = initUrl;
	document.getElementById("btn").onclick = getRequest;
}