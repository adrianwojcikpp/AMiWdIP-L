using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace filter_example.Model
{
    public class ServerMock
    {
        private double fs;
        private readonly double[] fcomp = { 0.1, 0.2, 0.7, 1.0 };
        private readonly Func<double, double> sin = Math.Sin;
        private readonly double pi = Math.PI;

        public ServerMock(double samplingFreq)
        {
            fs = samplingFreq;
        }

        public double getTestSignal(int n)
        {
            return sin(2 * pi * fcomp[0] * (n / fs)) + sin(2 * pi * fcomp[1] * (n / fs)) + sin(2 * pi * fcomp[2] * (n / fs)) + sin(2 * pi * fcomp[3] * (n / fs));
        }
    }
}
