const jsonInit = {
	temperature: { value: 20.3,		unit: "C" 	},
	pressure:	 { value: 1023.0,	unit: "hPa"	},
	humidity:	 { value: 63,		unit: "%"	}
}
var jsonTextInput, objTextOutput, jsonObj;

function parseJsonInput() {
	jsonObj = JSON.parse(jsonTextInput.value);
	var tempOutputDisp = "", presOutputDisp = "",humiOutputDisp = "";

	if(jsonObj.temperature != null)
		tempOutputDisp = "Temperature: " + jsonObj.temperature.value + jsonObj.temperature.unit + "<br>";
	if(jsonObj.pressure != null)
		presOutputDisp = "Pressure: "	 + jsonObj.pressure.value	 + jsonObj.pressure.unit + "<br>";
	if(jsonObj.humidity != null)
		humiOutputDisp = "Hummidity: "	 + jsonObj.humidity.value	 + jsonObj.humidity.unit + "<br>";
	
	objTextOutput.innerHTML = tempOutputDisp + presOutputDisp + humiOutputDisp;
}

window.onload = function() {
	jsonTextInput = document.getElementById("jsoninput"); 
	objTextOutput = document.getElementById("jsonoutput"); 
	jsonTextInput.value = JSON.stringify(jsonInit);
}
