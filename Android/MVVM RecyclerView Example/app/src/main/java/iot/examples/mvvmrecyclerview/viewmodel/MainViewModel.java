package iot.examples.mvvmrecyclerview.viewmodel;

// MODELS
import iot.examples.mvvmrecyclerview.model.MeasurementModel;
import iot.examples.mvvmrecyclerview.model.ServerIoT;
import iot.examples.mvvmrecyclerview.model.VolleyResponseListener;

import android.content.Context;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MeasurementsAdapter adapter;
    private List<MeasurementViewModel> measurements;
    private ServerIoT server;

    /**
     * @brief MainViewModel initialization: creation of measurement list view adapter, measurements
     *        list container and IoT server API configuration.
     * @param context Activity context
     */
    public void Init(Context context) {
        measurements = new ArrayList<>();
        adapter = new MeasurementsAdapter(measurements);

        server = new ServerIoT(context.getApplicationContext(),
                new VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Log.d("Response error", message);
                    }
                    @Override
                    public void onResponse(JSONArray response) {
                        int rs = response.length();
                        int ms = measurements.size();
                        int sizeDiff = ms - rs;
                        // remove redundant measurements from list
                        for( int i = 0; i < sizeDiff; i++) {
                            measurements.remove(ms - 1 - i);
                            adapter.notifyItemRemoved(ms - 1 - i);
                        }
                        // iterate through JSON Array
                        for (int i = 0; i < rs; i++) {
                            try {
                                /* get measurement model from JSON data */
                                MeasurementModel measurement = new MeasurementModel(response.getJSONObject(i));

                                /* update measurements list */
                                if(i >= ms) {
                                    measurements.add(measurement.toVM());
                                    adapter.notifyItemInserted(i);
                                } else {
                                    measurements.set(i, measurement.toVM());
                                    adapter.notifyItemChanged(i);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        server.setUrl("http://192.168.1.208/measurements.php");
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
        server.getMeasurements();
    }

    /**
     * @brief URL getter method.
     * @return Server resource URL
     */
    public String getUrl() {
        return server.getUrl();
    }

    /**
     * @brief URL setter method.
     * @param url Server resource URL
     */
    public void setUrl(String url) {
        server.setUrl(url);
    }

}