package iot.examples.firexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private double sampleTime;

    private GraphView chart; //!< GraphView object
    private LineGraphSeries[] signal;
    private RadioButton signalOption;

    private Timer filterTimer;

    private int sampleMax;

    private int k = 0; //!< Samples counter

    private boolean signalMock;

    /**** My FIR Low pass filter ***********************************************************/
    private MyFir filter = new MyFir(MyFirData.feedforward_coefficients, MyFirData.state);
    /**** Server mock **********************************************************************/
    private ServerMock serverMock = new ServerMock(1.0 / MyFirData.sampletime);
    /**** Server (RPi) ********************************************************************/
    private ServerIoT server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChartInit();

        signalOption = findViewById(R.id.op1);

        sampleTime = MyFirData.sampletime;
        sampleMax = (int)(chart.getViewport().getMaxX(false) / sampleTime);

        TextView sampleFreq = findViewById(R.id.sampleFreq);
        sampleFreq.setText(Double.toString(1.0 / sampleTime));

        server = new ServerIoT("192.168.56.5", this);
    }

    /**
     * @brief 'RUN FIR DEMO' button onClick event handler
     * @param v run_demo View from activity_main
     */
    public void RunButton(View v) {
        signalMock = signalOption.isChecked();

        if(filterTimer == null) {
            k = 0;
            server.resetRequestCounter();

            signal[0].resetData(new DataPoint[]{});
            signal[1].resetData(new DataPoint[]{});

            filterTimer = new Timer();
            TimerTask filterTimerTask = new TimerTask() {
                public void run() { FilterProcedure(); }
            };
            filterTimer.scheduleAtFixedRate(filterTimerTask, 0, (int)(sampleTime*1000));
        }
    }

    /**
     * @brief Demo of signal filtering procedure using the FIR filter.
     */
    private void  FilterProcedure() {
        if (k <= sampleMax) {
            // get signal
            final Double x;
            if (signalMock) {
                // from mock object
                x = serverMock.getTestSignal(k);
            } else {
                // from server
                x = server.getTestSignal(k);
            }
            // filter signal
            final Double xf = filter.Execute(x);
            // display data (GraphView)
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    signal[0].appendData(new DataPoint(k * sampleTime, x), false, sampleMax);
                    signal[1].appendData(new DataPoint(k * sampleTime, xf), false, sampleMax);
                    chart.onDataChanged(true, true);
                }
            });
            // update time
            k++;
        } else {
            filterTimer.cancel();
            filterTimer = null;
        }
    }

    /**
     * @brief Chart initialization.
     */
    private void ChartInit() {
        // https://github.com/jjoe64/GraphView/wiki
        chart = (GraphView)findViewById(R.id.chart);
        signal = new LineGraphSeries[]{ new LineGraphSeries<>(new DataPoint[]{}),
                new LineGraphSeries<>(new DataPoint[]{}) };
        chart.addSeries(signal[0]);
        chart.addSeries(signal[1]);
        chart.getViewport().setXAxisBoundsManual(true);
        chart.getViewport().setMinX(0.0);
        chart.getViewport().setMaxX(50.0);
        chart.getViewport().setYAxisBoundsManual(true);
        chart.getViewport().setMinY(-3.0);
        chart.getViewport().setMaxY(3.0);

        signal[0].setTitle("Original test signal");
        signal[0].setColor(Color.BLUE);
        signal[1].setTitle("Filtered test signal");
        signal[1].setColor(Color.GREEN);

        chart.getLegendRenderer().setVisible(true);
        chart.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        chart.getLegendRenderer().setTextSize(30);

        chart.getGridLabelRenderer().setTextSize(20);
        chart.getGridLabelRenderer().setVerticalAxisTitle(Space(7) + "Amplitude [-]");
        chart.getGridLabelRenderer().setHorizontalAxisTitle(Space(11) + "Time [s]");
        chart.getGridLabelRenderer().setNumHorizontalLabels(9);
        chart.getGridLabelRenderer().setNumVerticalLabels(7);
        chart.getGridLabelRenderer().setPadding(35);
    }

    /**
     * @param n Number of spaces.
     * @retval String with 'n' spaces.
     */
    private String Space(int n) {
        return new String(new char[n]).replace('\0', ' ');
    }
}
