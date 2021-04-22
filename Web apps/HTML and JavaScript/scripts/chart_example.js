const sampleTime = 0.001; ///< sample time in sec
var xdata; ///< x-axis labels array: time stamps
var ydata; ///< y-axis data array: random value
var lastTimeStamp; ///< most recent time stamp 
var chartContext;  ///< chart context i.e. object that "owns" chart
var chart; ///< Chart.js object

/**
* @brief Generate random value.
* @retval random number from range <-1, 1>
*/
function getRand() {
	const maxVal =  1.0;
	const minVal = -1.0;
	return (maxVal-minVal)*Math.random() + minVal;
}

/**
* @brief Add new random value to next data point.
*/
function addRandData(){
	lastTimeStamp += sampleTime;
	chart.data.labels.push(lastTimeStamp.toFixed(4));
	chart.data.datasets[0].data.push(getRand());
	chart.update();
}

/**
* @brief Remove oldest data point.
*/
function removeData(){
	chart.data.labels.splice(0,1);
	chart.data.datasets[0].data.splice(0,1);
	chart.update();
}

window.onload = function() {

	// array with ten consecutive integers: <0, 9>
	xdata = [...Array(10).keys()]; 
	// scaling all values ​​times the sample time 
	xdata.forEach(function(p, i) {this[i] = (this[i]*sampleTime).toFixed(4);}, xdata);

	// set most recent time stamp as last value of 'xdata'
	lastTimeStamp = +xdata[xdata.length-1]; 

	// array with ten 'undefined' elements
	ydata = [...new Array(xdata.length)]; 
	// setting randowm values to all elements with local 'getRand' function
	ydata.forEach(function(p, i) { this[i] = getRand(); }, ydata); 

	// get chart context from 'canvas' element
	chartContext = document.getElementById('chart').getContext('2d');

	chart = new Chart(chartContext, {
		// The type of chart: linear plot
		type: 'line',

		// Dataset: 'xdata' as labels, 'ydata' as dataset.data
		data: {
			labels: xdata,
			datasets: [{
				fill: false,
				label: 'Random timeseries',
				backgroundColor: 'rgb(0, 0, 255)',
				borderColor: 'rgb(255, 0, 0)',
				data: ydata,
				lineTension: 0
			}]
		},

		// Configuration options
		options: {
			responsive: true,
			maintainAspectRatio: false,
			scales: {
				yAxes: [{
					scaleLabel: {
						display: true,
						labelString: 'Random value'
					}
				}],
				xAxes: [{
					scaleLabel: {
						display: true,
						labelString: 'Time [s]'
					}
				}]
			}
		}
	}
	);
};