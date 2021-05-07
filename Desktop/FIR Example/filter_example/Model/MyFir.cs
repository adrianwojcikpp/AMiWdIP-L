using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace filter_example.Model
{
    public class MyFir
    {
        private double[] feedforward_coefficients; //!< Array of FIR filter feedforward coefficients
        private List<double> state;                //!< List of FIR filter state values

        /**
          * @brief Initialization of FIR filter algorithm. 
          * @param ffc Array of FIR filter feedforward coefficients.
          * @param st Array of FIR filter input initial state.
          *        Must be the same size as coefficients array.
          */
        public MyFir(double[] ffc, double[] st)
        {
            feedforward_coefficients = ffc;
            state = new List<double>(st);
        }

        /**
         * @brief Execute one step of the FIR filter algorithm.
         * @param x Input signal.
         * @retval Output [filtered] signal.
         */
        public double Execute(double x)
        {
            // update state
            state.Insert(0, x);
            state.RemoveAt(state.Count - 1);
            // compute output
            double xf = 0.0;
            for (int i = 0; i < state.Count; i++)
            {
                xf += feedforward_coefficients[i] * state[i];
            }
            return xf;
        }
    }
}
