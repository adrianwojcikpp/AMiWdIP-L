package iot.examples.mvvmrecyclerview.viewmodel;

// MODELS
import iot.examples.mvvmrecyclerview.model.MeasurementModel;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.lifecycle.ViewModel;

public class MainViewModelMock extends ViewModel {

    private MeasurementsAdapter adapter;
    private List<MeasurementViewModel> measurements;

    /**
     * @brief MainViewModel initialization: creation of measurement list view adapter, measurements
     *        list container and IoT server API configuration.
     * @param context Activity context
     */
    public void Init(Context context) {
        measurements = new ArrayList<>();

        MeasurementModel temperature = new MeasurementModel("temperature", 32.131, "C");
        measurements.add(temperature.toVM());

        MeasurementModel pressure = new MeasurementModel("pressure", 1016.123, "hPa");
        measurements.add(pressure.toVM());

        MeasurementModel humidity = new MeasurementModel("humidity", 32.421, "%");
        measurements.add(humidity.toVM());

        MeasurementModel random = new MeasurementModel("random", 0.5, "-");
        measurements.add(random.toVM());

        adapter = new MeasurementsAdapter(measurements);
    }

    /**
     * @brief Getter of 'adapter' field.
     * @return Measurement list view adapter.
     */
    public MeasurementsAdapter getAdapter() {
        return adapter;
    }

    /**
     * @brief 'Refresh' button onClick event handler.
     * @param v 'Refresh' button view
     */
    public void updateMeasurements(View v) {
        Random rnd = new Random();

        MeasurementModel temperature = new MeasurementModel("temperature", 32.131 + rnd.nextDouble()*3.0, "C");
        measurements.set(0, temperature.toVM());
        adapter.notifyItemChanged(0);

        MeasurementModel pressure = new MeasurementModel("pressure", 1016.123 + rnd.nextDouble()*10.0, "hPa");
        measurements.set(1, pressure.toVM());
        adapter.notifyItemChanged(1);

        MeasurementModel humidity = new MeasurementModel("humidity", 32.421 + rnd.nextDouble()*5.0, "%");
        measurements.set(2, humidity.toVM());
        adapter.notifyItemChanged(2);

        MeasurementModel random = new MeasurementModel("random", rnd.nextDouble(), "-");
        measurements.set(3, random.toVM());
        adapter.notifyItemChanged(3);
    }

    /**
     * @brief URL getter method.
     * @return Server resource URL
     */
    public String getUrl() {
        return "http://mocskerver/measurements.php";
    }

    /**
     * @brief URL setter method.
     * @param url Server resource URL
     */
    public void setUrl(String url) {

    }
}

