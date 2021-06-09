const sampleTimeSec = 0.1; 					///< sample time in sec
const sampleTimeMsec = 1000*sampleTimeSec;	///< sample time in msec
const maxSamplesNumber = 1000;				///< maximum number of samples

var timeVec;  ///< x-axis labels array: time stamps
var rollVec;  ///< y-axis data array: roll
var pitchVec; ///< y-axis data array: pitch
var yawVec;   ///< y-axis data array: yaw
var lastTimeStamp; ///< most recent time stamp 

var chartContext;  ///< chart context i.e. object that "owns" chart
var chart; ///< Chart.js object

var timer; ///< request timer

const url = 'http://192.168.0.100/sensors_via_deamon.php?id=rpy'

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
function addData(r,p,y){
	if(rollVec.length > maxSamplesNumber)
	{
		removeOldData();
		lastTimeStamp += sampleTimeSec;
		timeVec.push(lastTimeStamp.toFixed(4));
	}
	rollVec.push(r);
	pitchVec.push(p);
	yawVec.push(y);
	chart.update();
}

/**
* @brief Remove oldest data point.
*/
function removeOldData(){
	timeVec.splice(0,1);
	rollVec.splice(0,1);
	pitchVec.splice(0,1);
	yawVec.splice(0,1);
}

/**
* @brief Start request timer
*/
function startTimer(){
	if(timer == null)
		timer = setInterval(ajaxJSON, sampleTimeMsec);
}

/**
* @brief Stop request timer
*/
function stopTimer(){
	if(timer != null) {
		clearInterval(timer);
		timer = null;
	}
}

/**
* @brief Send HTTP GET request to IoT server
*/
function ajaxJSON() {
	$.ajax(url, {
		type: 'GET', dataType: 'json',
		success: function(responseJSON, status, xhr) {
			addData(+responseJSON[0].value, +responseJSON[1].value, +responseJSON[2].value);
		}
	});
}

/**
* @brief Chart initialization
*/
function chartInit()
{
	// array with consecutive integers: <0, maxSamplesNumber-1>
	timeVec = [...Array(maxSamplesNumber).keys()]; 
	// scaling all values ​​times the sample time 
	timeVec.forEach(function(p, i) {this[i] = (this[i]*sampleTimeSec).toFixed(4);}, timeVec);

	// last value of 'timeVec'
	lastTimeStamp = +timeVec[timeVec.length-1]; 

	// empty array
	rollVec = []; 
	pitchVec = []; 
	yawVec = []; 

	// get chart context from 'canvas' element
	chartContext = $("#chart")[0].getContext('2d');

	Chart.defaults.global.elements.point.radius = 1;
	
	chart = new Chart(chartContext, {
		// The type of chart: linear plot
		type: 'line',

		// Dataset: 'timeVec' as labels, 'rollVec' as dataset.data
		data: {
			labels: timeVec,
			datasets: [
			{
				fill: false,
				label: 'Roll',
				backgroundColor: 'rgb(255, 0, 0)',
				borderColor: 'rgb(255, 0, 0)',
				data: rollVec,
				lineTension: 0
			},
			{
				fill: false,
				label: 'Pitch',
				backgroundColor: 'rgb(0, 255, 0)',
				borderColor: 'rgb(0, 255, 0)',
				data: pitchVec,
				lineTension: 0
			},
						{
				fill: false,
				label: 'Yaw',
				backgroundColor: 'rgb(0, 0, 255)',
				borderColor: 'rgb(0, 0, 255)',
				data: yawVec,
				lineTension: 0
			}
			]
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
						labelString: 'Angular position [-]'
					},
					ticks: {
						suggestedMin: 0,
						suggestedMax: 360 
					}
				}],es: [{
					scaleLabel: {
						display: true,
						labelString: 'Time [s]'
					}
				}]
			}
		}
	});
	
	rollVec = chart.data.datasets[0].data;
	pitchVec = chart.data.datasets[1].data;
	yawVec = chart.data.datasets[2].data;
	timeVec = chart.data.labels;
	
	//$.ajaxSetup({ cache: false });
}

$(document).ready(() => { 
	timer = null;
	chartInit();
	$("#start").click(startTimer);
	$("#stop").click(stopTimer);
	$("#sampletime").text(sampleTimeMsec.toString());
	$("#samplenumber").text(maxSamplesNumber.toString());
});