package iot.examples.firexample;
import java.lang.Math;

public class ServerMock  {
    private Double fs;
    private final Double[] fcomp = {0.1, 0.2, 0.7, 1.0};
    private Double sin(Double x) { return Math.sin(x); }
    private final Double pi = Math.PI;

    public ServerMock(Double samplingFreq) {
        fs = samplingFreq;
    }

    public Double getTestSignal(int n) {
        return sin(2 * pi * fcomp[0] * (n / fs))
                + sin(2 * pi * fcomp[1] * (n / fs))
                + sin(2 * pi * fcomp[2] * (n / fs))
                + sin(2 * pi * fcomp[3] * (n / fs));
    }
}
