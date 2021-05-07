/**
 * @brief Unit Tests of 'MyFir' class.
 */
var MyFirUnitTest = {
	/**
	 * @brief Unit Tests of 'Execute' method.
	 *		  Calculates RMSE of the filter response
	 *	 	  relative to the original design (Octave).
	 *		  Error tolerance: 10^(-10).
	 */
	ExecuteTest: () => {
		let maxError = 1e-20;
		let meanError = 0; 
		let N = MyFirTestData.Input.length;
		
		let filter = new MyFir(MyFirData.feedforward_coefficients, MyFirData.state);
		
		for(let k = 0; k < N; k++){
			let output = filter.Execute(MyFirTestData.Input[k]);
			let outDelta = (MyFirTestData.RefOutput[k] - output);
			meanError += outDelta*outDelta;
		}
		meanError = Math.sqrt(meanError/N);
		
		unitjs.number(meanError).isBetween(0, maxError); //!< Unit.js 
	}
}