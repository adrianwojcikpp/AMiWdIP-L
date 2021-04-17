package iot.examples.mvvmrecyclerview.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class ServerIoT {

    /* Server resources */
    private String url;

    private VolleyResponseListener listener;
    private RequestQueue queue;

    /**
     * @brief ServerIoT parametric constructor.
     * @param context HTTP request execution context. This ensures that the RequestQueue will last
     *                for the lifetime of an app, instead of being recreated every time the
     *                activity is recreated (for example, when the user rotates the device).
     * @param volleyResponseListener Custom response listener interface.
     */
    public ServerIoT(Context context, VolleyResponseListener volleyResponseListener) {
        queue = Volley.newRequestQueue(context.getApplicationContext());
        listener = volleyResponseListener;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String _url) {
        /* TODO: Add URL validation */
        url = _url;
    }

    /**
     * @brief Server IoT listener getter.
     * @return request listener.
     */
    public VolleyResponseListener getListener() {
        return listener;
    }

    /**
     * @brief Get environment sensors measurement: temperature, pressure & humidity.
     */
    public void getMeasurements()  {
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Call ViewModel response listener
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        String msg = error.getMessage();
                        // Call ViewModel error listener
                        if(msg != null)
                            listener.onError(msg);
                        else
                            listener.onError("UNKNOWN ERROR");
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(request);
    }
}
