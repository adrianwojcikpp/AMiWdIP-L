var buttonCounter = 0; ///< button onClick event counter

function myOnClickMethodFirstClick() {
	buttonCounter += 1;
	document.getElementById("s1").innerHTML = "<b>Click counter: </b>";
	document.getElementById("s2").innerHTML = buttonCounter.toString();
	document.getElementById("btn").onclick  = myOnClickMethod;
}

function myOnClickMethod() {
	buttonCounter += 1;
	document.getElementById("s2").innerHTML = buttonCounter.toString();
}

window.onload = ()=>{
	document.getElementById("paragraph").innerHTML = '<span id="s1"></span><span id="s2"></span>';
	document.getElementById("btn").onclick  = myOnClickMethodFirstClick;
}