var chartContext;  //!< Chart context i.e. object that "owns" chart
var chart;         //!< Chart.js object
var signal;        //!< Original and filtered signals arrays
var time;          //!< Timestamps array

var filterTimer = null;

var samplesMax;

var k = 0; //!< Samples counter


var signalMock; 

/**** My FIR Low pass filter ****************************************************/
var filter = new MyFir(MyFirData.feedforward_coefficients, MyFirData.state);
/**** Test signal ***************************************************************/
var serverMock = new ServerMock(1/MyFirData.sampletime);
/**** Server (RPi) **************************************************************/
var server;

/**
 * @brief This method will run once the page DOM 
 *        is ready to execute JavaScript code.
 */
$(document).ready(() => { 

	ChartInit();
	
	$("#start").click(RunDemo);
	
	$("#samplefreq").text(1.0/MyFirData.sampletime);
	samplesMax = time.length;
	
	server = new ServerIoT("localhost");
});

/**
 * @brief Demo of signal filtering procedure using the FIR filter.
 */
async function FilterProcedure() {
	if( k <= samplesMax ){
		// get signal
		if(signalMock) {
			// from TestSignal object
			x = serverMock.getTestSignal(k);
		} else {
			// from server 
			x = await server.getTestSignal(k)
		}
		// filter signal
		xf = filter.Execute(x);
		// display data (Chart.js)
		signal[1].push(x);
		signal[0].push(xf);
		chart.update();
		// update time
		k++;
	} else {
		clearInterval(filterTimer);
		filterTimer = null;
	}
}

/**
 * @brief Start filter filterTimer
 */
function RunDemo(){
	
	signalMock = $("#op1").is(":checked");
	
	if (filterTimer == null){
		k = 0;
		signal[0].splice(0,signal[0].length);
		signal[1].splice(0,signal[1].length);
		chart.update();
		filterTimer = setInterval(FilterProcedure, MyFirData.sampletime * 1000); 
	}
}

/**
 * @brief Chart initialization
 */
function ChartInit()
{
	// array with consecutive integers: <0, maxSamplesNumber-1>
	time = [...Array(500).keys()]; 
	// scaling all values ​​times the sample time 
	time.forEach(function(p, i) {this[i] = (this[i]*MyFirData.sampletime).toFixed(1);}, time);

	// get chart context from 'canvas' element
	chartContext = $("#chart")[0].getContext('2d');
	
	Chart.defaults.global.elements.point.radius = 1;

	chart = new Chart(chartContext, {
		// The type of chart: linear plot
		type: 'line',

		// Dataset: 'xdata' as labels, 'signal1' as dataset.data
		data: {
			labels: time,
			datasets: [{
				fill: false,
				label: 'Filtered test signal',
				backgroundColor: 'rgb(0, 255, 0)',
				borderColor: 'rgb(0, 255, 0)',
				data: [],
				lineTension: 0
			},
			{
				fill: false,
				label: 'Original test signal',
				backgroundColor: 'rgb(0, 0, 255)',
				borderColor: 'rgb(0, 0, 255)',
				data: [],
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
						labelString: 'Amplitude [-]'
					},
					ticks: {
						suggestedMin: -3,
						suggestedMax: 3 
					}
				}],
				xAxes: [{
					scaleLabel: {
						display: true,
						labelString: 'Time [sec]'
					},
					ticks: {
						suggestedMin: 0,
						suggestedMax: 50 
					}
				}]
			}
		}
	});
	
	signal = [chart.data.datasets[0].data,
			  chart.data.datasets[1].data];
	time = chart.data.labels
}