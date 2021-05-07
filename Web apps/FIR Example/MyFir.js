/**
 * @brief Implementation of finite impulse response (FIR) filter.
 */
class MyFir {
	
	/**
	 * @brief Initialization of FIR filter algorithm. 
	 * @param ffc Array of FIR filter feedforward coefficients.
	 * @param st Array of FIR filter input initial state.
	 *        Must be the same size as coefficients array.
	 */
	 constructor(ffc, st) {
		this.feedforward_coefficients = ffc;
		this.state = st;
	 }
	 
	/**
	 * @brief Execute one step of the FIR filter algorithm.
	 * @param x Input signal.
	 * @retval Output [filtered] signal.
	 */
	Execute(x) {
		// update state
		this.state.unshift(x);
		this.state.pop();
		// compute output
		let xf = 0.0;
		for (let i = 0; i < this.state.length; i++) {
			xf += this.feedforward_coefficients[i]*this.state[i];
		}
		return xf;
	}
}