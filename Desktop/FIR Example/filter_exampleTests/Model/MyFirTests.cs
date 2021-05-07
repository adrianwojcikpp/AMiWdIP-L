using Microsoft.VisualStudio.TestTools.UnitTesting;
using filter_example.Model;
using System;

namespace filter_example.Model.Tests
{
    /**
      * @brief Unit Tests of 'MyFir' class.
      */
    [TestClass()]
    public class MyFirTests
    {
        /**
         * @brief Unit Tests of 'Execute' method.
         *        Calculates RMSE of the filter response
         *        relative to the original design (Octave).
         *        Error tolerance: 10^(-10).
         */
        [TestMethod()]
        [Timeout(100)]  // [ms]
        public void ExecuteTest()
        {
            double maxError = 1e-10;
            double meanError = 0;
            int N = MyFirTestData.Input.Length;

            MyFir filter = new MyFir(MyFirData.feedforward_coefficients, MyFirData.state);

            for (int k = 0; k < N; k++)
            {
                double output = filter.Execute(MyFirTestData.Input[k]);
                double outDelta = (MyFirTestData.RefOutput[k] - output);
                meanError += outDelta * outDelta;
            }
            meanError =  Math.Sqrt(meanError/N);
            Assert.AreEqual(0.0, meanError, maxError); //!< MSTest V2
        }
    }
}