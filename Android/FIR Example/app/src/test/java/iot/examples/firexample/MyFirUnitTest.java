package iot.examples.firexample;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @brief Unit Tests of 'MyFir' class.
 */
public class MyFirUnitTest {
    /**
     * @brief Unit Tests of 'Execute' method.
     *        Calculates RMSE of the filter response
     *        relative to the original design (Octave).
     *        Error tolerance: 10^(-10).
     */
    @Test
    public void ExecuteTest() {
        double maxError = 1e-10;
        double meanError = 0;
        int N = MyFirTestData.Input.length;

        MyFir filter = new MyFir(MyFirData.feedforward_coefficients, MyFirData.state);

        for(int k = 0; k < N; k++){
            double output = filter.Execute(MyFirTestData.Input[k]);
            double outDelta = (MyFirTestData.RefOutput[k] - output);
            meanError += outDelta*outDelta;
        }
        meanError = Math.sqrt(meanError/N);
        assertEquals(0, meanError, maxError); ///< JUnit
    }
}
