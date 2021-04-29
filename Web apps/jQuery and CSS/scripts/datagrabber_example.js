const sampleTimeSec = 0.1;                  ///< sample time in sec
const sampleTimeMsec = 1000*sampleTimeSec;  ///< sample time in msec
const maxSamplesNumber = 100;               ///< maximum number of samples

var xdata; ///< x-axis labels array: time stamps
var ydata; ///< y-axis data array: random value
var lastTimeStamp; ///< most recent time stamp 

var chartContext;  ///< chart context i.e. object that "owns" chart
var chart;         ///< Chart.js object

var timer; ///< request timer

const url = 'http://localhost/server mock/chartdata.json'; ///< server app with JSON API
//const url = 'http://' + window.location.hostname + '/nocache/chartdata.json'

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
* @brief Add new value to next data point.
* @param y New y-axis value 
*/
function addData(y){
	if(ydata.length > maxSamplesNumber)
	{
		removeOldData();
		lastTimeStamp += sampleTimeSec;
		xdata.push(lastTimeStamp.toFixed(4));
	}
	ydata.push(y);
	chart.update();
}

/**
* @brief Remove oldest data point.
*/
function removeOldData(){
	xdata.splice(0,1);
	ydata.splice(0,1);
}

/**
* @brief Start request timer
*/
function startTimer(){
	timer = setInterval(ajaxJSON, sampleTimeMsec);
}

/**
* @brief Stop request timer
*/
function stopTimer(){
	clearInterval(timer);
}

/**
* @brief Send HTTP GET request to IoT server
*/
function ajaxJSON() {
	$.ajax(url, {
		type: 'GET', dataType: 'json',
		success: function(responseJSON, status, xhr) {
			addData(+responseJSON.data);
		}
	});
}

/**
* @brief Chart initialization
*/
function chartInit()
{
	// array with consecutive integers: <0, maxSamplesNumber-1>
	xdata = [...Array(maxSamplesNumber).keys()]; 
	// scaling all values ​​times the sample time 
	xdata.forEach(function(p, i) {this[i] = (this[i]*sampleTimeSec).toFixed(4);}, xdata);

	// last value of 'xdata'
	lastTimeStamp = +xdata[xdata.length-1]; 

	// empty array
	ydata = []; 

	// get chart context from 'canvas' element
	chartContext = $("#chart")[0].getContext('2d');

	chart = new Chart(chartContext, {
		// The type of chart: linear plot
		type: 'line',

		// Dataset: 'xdata' as labels, 'ydata' as dataset.data
		data: {
			labels: xdata,
			datasets: [{
				fill: false,
				label: 'Random timeseries',
				backgroundColor: 'rgb(255, 0, 0)',
				borderColor: 'rgb(255, 0, 0)',
				data: ydata,
				lineTension: 0
			}]
		},

		// Configuration options
		options: {
			responsive: true,
			maintainAspectRatio: false,
			animation: false,
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
	});
	
	ydata = chart.data.datasets[0].data;
	xdata = chart.data.labels;
	
	//$.ajaxSetup({ cache: false });
}

$(document).ready(() => { 
	chartInit();
	$("#start").click(startTimer);
	$("#stop").click(stopTimer);
	$("#sampletime").text(sampleTimeMsec.toString());
	$("#samplenumber").text(maxSamplesNumber.toString());
});