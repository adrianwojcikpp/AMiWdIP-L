const resultOutputId = "result";		    ///< result output element ID
var firstArg = null, secondArg = null;	///< arithmetic operation arguments
var activeOperation = null;				      ///< selected arithmetic operation 
var operationSymbols = {				///< arithmetic operations display symbols
	div: "/", 
	prod: "*", 
	sub: "-", 
	add: "+"
}; 
var operationFunctions = {			///< arithmetic operations lambda expressions
	div:  (a,b) => {return (a/b);},
	prod: (a,b) => {return (a*b);},
	sub:  (a,b) => {return (a-b);},
	add:  (a,b) => {return (a+b);}
};

/**
* @brief onClic event handling for numeric keypad: digits and decimal point
* @param num Numeric keypad element ID
*/
function numpad(num) {
	var result = document.getElementById(resultOutputId);
	
	if(result.value == "0" && num != "dot") {
		result.value = "";
	}
	
	let dispText = result.value;
	
	if(num == "dot") {
		result.value = dispText + ".";
	} else {
		result.value = dispText + num;
	}
}

/**
* @brief 'onClick' event handling for numeric keypad: arithmetic operations.
*		 If called before getting result computes previous arithmetic operation.
* @param op Numeric keypad element ID
*/
function arithmeticOperations(op) {
	var result = document.getElementById(resultOutputId);
	
	if(activeOperation != null) {
		secondArg = result.value.substring(firstArg.length+1);
		result.value = operationFunctions[activeOperation](+firstArg,+secondArg).toString();
	}

	activeOperation = op;
	firstArg = result.value;
	result.value += operationSymbols[activeOperation];
}

/**
* @brief 'onClick' event handling for 'equals' button.
*		 Computes selected arithmetic operation.
*/
function getResult() {
	var result = document.getElementById(resultOutputId);
	
	secondArg = result.value.substring(firstArg.length+1);
	result.value = operationFunctions[activeOperation](+firstArg,+secondArg).toString();
	activeOperation = null;
	firstArg = result.value;
}

/**
* @brief 'onClick' event handling for 'C' button.
*		 Clears saved arguments and sets result to '0'.
*/
function clearResult() {
	var result = document.getElementById(resultOutputId);
	
	result.value = "0";
	activeOperation = null;
	firstArg = null;
	secondArg = null;
}