class ServerIoT {
	constructor(ip){
		this.protocol = 'http://';
		this.ip = ip + '/';
		this.script = 'server/test_signal.php'; // 'cgi-bin/server/test_signal.py'
		this.signalValue = 0;
	}
	async getTestSignal(sampleNumber) {
    
		await $.get(this.protocol + this.ip + this.script, 
			{ k : sampleNumber }, 
			(response) => { this.signalValue = +response}
		)
		return this.signalValue;
	}
}