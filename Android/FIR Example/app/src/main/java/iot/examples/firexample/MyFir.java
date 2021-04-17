package iot.examples.firexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFir {

    private Double[] feedforward_coefficients;  //!< Array of FIR filter feedforward coefficients
    private List<Double> state;                 //!< List of FIR filter state values

    /**
     * @brief Initialization of FIR filter algorithm.
     * @param ffc Array of FIR filter feedforward coefficients.
     * @param st Array of FIR filter input initial state.
     *        Must be the same size as coefficients array.
     */
    public MyFir(Double[] ffc, Double[] st) {
        feedforward_coefficients = ffc;
        state = new ArrayList<>(Arrays.asList(st));
    }

    /**
     * @brief Execute one step of the FIR filter algorithm.
     * @param x Input signal.
     * @retval Output [filtered] signal.
     */
    public Double Execute(Double x) {
        // update state
        state.add(0, x);
        state.remove(state.size() - 1);
        // compute output
        Double xf = 0.0;
        for (int i = 0; i < state.size(); i++) {
            xf += feedforward_coefficients[i] * state.get(i);
        }
        return xf;
    }
}

