var buttonCounter = 0; ///< button onClick event counter

/**
* @brief document onReady event handling
*/
$(document).ready(()=>{
	/**
	* @brief button onClick event handling
	*/
	$("#btn").click(()=>{
		buttonCounter += 1;
		$("#paragraph").html("<b>Click counter: </b>" + buttonCounter.toString());
	});
});