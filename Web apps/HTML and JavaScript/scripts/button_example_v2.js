var buttonCounter = 0; ///< button onClick event counter

/**
* @brief button onClick event handling
*/
function myOnClickMethod() {
	buttonCounter += 1;
	var paragraphDispText = "<b>Click counter: </b>" + buttonCounter.toString();
	document.getElementById("paragraph").innerHTML = paragraphDispText;
}