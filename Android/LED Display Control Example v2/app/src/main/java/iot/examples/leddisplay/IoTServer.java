/**
 ******************************************************************************
 * @file    LED Display Control Example/IoTServer.java
 * @author  Adrian Wojcik
 * @version V1.1
 * @date    04-May-2021
 * @brief   LED display controller: IoT server model
 ******************************************************************************
 */

package iot.examples.leddisplay;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class IoTServer {
    private final String ip;
    private RequestQueue queue; ///< HTTP requests queue

    public IoTServer(String _ip, Context context)
    {
        ip = _ip;
        queue = Volley.newRequestQueue(context);
    }

    /**
     * @brief obtaining the address of the PHP script from IoT server IP.
     * @return LED display control script URL
     */
    public String getScriptUrl() {
        return "http://" + ip + "/server/led_display.php";
    }

    /**
     * @brief Send control request using Volley queue with PUT method
     * @param data Control data in JSON format
     */
    public void putControlRequest(JSONArray data) {
        JsonArrayRequest putRequest = new JsonArrayRequest(Request.Method.PUT, getScriptUrl(), data,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        putRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(putRequest);
    }

}
