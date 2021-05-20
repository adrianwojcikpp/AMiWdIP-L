using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using OxyPlot;
using OxyPlot.Axes;
using OxyPlot.Series;
using System.Timers;


namespace filter_example.ViewModel
{
    using Model;

    public class MainViewModel : INotifyPropertyChanged
    {
        private double sampleTime;
        public string SampleFreq
        {
            get
            {
                return (1.0 / sampleTime).ToString();
            }
            set
            {
                if (double.TryParse(value, out double sf))
                {
                    if (sampleTime != (1.0 / sf))
                    {
                        sampleTime = (1.0 / sf);
                        OnPropertyChanged("SampleFreq");
                    }
                }
            }
        }

        public PlotModel chart { get; set; } //!< OxyPlot ViewModel
        public ButtonCommand RunButton { get; set; }

        private MultimediaTimer filterTimer;

        private double samplesMax;

        private int k = 0; //!< Samples counter

        private bool signalMock = true;
        public bool SignalMock
        {
            get
            {
                return signalMock;
            }
            set
            {
                if (signalMock != value)
                {
                    signalMock = value;
                    OnPropertyChanged("SignalLocal");
                }
            }
        }

        /**** My FIR Low pass filter ***********************************************************/
        private MyFir filter = new MyFir(MyFirData.feedforward_coefficients, MyFirData.state);
        /**** Server mock  *********************************************************************/
        private ServerMock serverMock = new ServerMock(1.0 / MyFirData.sampletime);
        /**** Server (RPi) *********************************************************************/
        private ServerIoT server;

        public MainViewModel()
        {
            ChartInit();

            RunButton = new ButtonCommand(RunDemo);

            sampleTime = MyFirData.sampletime;
            samplesMax = chart.Axes[0].Maximum / sampleTime;

            server = new ServerIoT("192.168.56.5");
        }

        /**
          * @brief Demo of signal filtering procedure using the FIR filter.
          */
        private async void FilterProcedure(object sender, EventArgs e)
        {
            if (k <= samplesMax) {
                // get signal
                double x;
                if (signalMock) {
                    // from mock object
                    x = serverMock.getTestSignal(k);
                } else {
                    // from server
                    x = await server.getTestSignal(k);
                }
                // filter signal
                double xf = filter.Execute(x);
                // display data (OxyPlot)
                (chart.Series[0] as LineSeries).Points.Add(new DataPoint(k * sampleTime, x));
                (chart.Series[1] as LineSeries).Points.Add(new DataPoint(k * sampleTime, xf));
                chart.InvalidatePlot(true);
                // update time
                k++;
            } else {
                filterTimer.Stop();
                filterTimer = null;
            }
        }

        /**
          * @brief 'Run FIR demo' button command.
          */
        private void RunDemo()
        {
            if (filterTimer == null)
            {
                k = 0;

                (chart.Series[0] as LineSeries).Points.Clear();
                (chart.Series[1] as LineSeries).Points.Clear();

                filterTimer = new MultimediaTimer(1000 * sampleTime);
                filterTimer.Elapsed += new EventHandler(FilterProcedure);
                filterTimer.Start();
            }
        }

        /**
         * @brief Chart initalization.
         */ 
        private void ChartInit()
        {
            chart = new PlotModel { Title = "Simple client-side data processing example" };

            chart.Axes.Add(new LinearAxis()
            {
                Position = AxisPosition.Bottom,
                Minimum = 0,
                Maximum = 50,
                Key = "Horizontal",
                Unit = "sec",
                Title = "Time",
                MajorStep = 5,
                MajorGridlineColor = OxyColor.Parse("#FFD3D3D3"),
                MajorGridlineStyle = LineStyle.Solid,
                MajorGridlineThickness = 1
            });
            chart.Axes.Add(new LinearAxis()
            {
                Position = AxisPosition.Left,
                Minimum = -3,
                Maximum = 3.5,
                Key = "Vertical",
                Unit = "-",
                Title = "Amplitude",
                MajorStep = 1,
                MajorGridlineColor = OxyColor.Parse("#FFD3D3D3"),
                MajorGridlineStyle = LineStyle.Solid,
                MajorGridlineThickness = 1
            });

            chart.Series.Add(new LineSeries()
            {
                Title = "Orginal test signal",
                Color = OxyColor.Parse("#FF0000FF")
            });
            chart.Series.Add(new LineSeries()
            {
                Title = "Filtered test signal",
                Color = OxyColor.Parse("#FF00FF00")
            });
        }

        #region PropertyChanged

        public event PropertyChangedEventHandler PropertyChanged;

        /**
         * @brief Simple function to trigger event handler
         * @params propertyName Name of ViewModel property as string
         */
        protected void OnPropertyChanged(string propertyName)
        {
            PropertyChangedEventHandler handler = PropertyChanged;
            if (handler != null) handler(this, new PropertyChangedEventArgs(propertyName));
        }

        #endregion
    }
}
