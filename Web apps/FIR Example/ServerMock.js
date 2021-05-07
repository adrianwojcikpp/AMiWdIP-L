/**
 * @brief 
 */
class ServerMock {
	
	fcomp = [0.1, 0.2, 0.7, 1.0]; ///<
	sin = Math.sin;				  ///<
	pi = Math.PI;				  ///<
	
	/**
	 * @brief 
	 * @param samplingFreq 
	 */
	constructor(samplingFreq) {
		this.fs = samplingFreq;
	}
	
	/**
	 * @brief 
	 * @param n 
	 * @retval 
	 */
	getTestSignal(n) {
		// local aliases
		let fs = this.fs;
		let fcomp = this.fcomp;
		let sin = this.sin;
		let pi = this.pi;
		
		return sin(2*pi*fcomp[0]*(n/fs)) + sin(2*pi*fcomp[1]*(n/fs)) + sin(2*pi*fcomp[2]*(n/fs)) + sin(2*pi*fcomp[3]*(n/fs));
	}
}