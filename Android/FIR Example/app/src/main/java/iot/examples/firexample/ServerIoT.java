package iot.examples.firexample;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ServerIoT {

    private String protocol = "http://";
    private String ip;
    private String script = "server/test_signal.php"; // "cgi-bin/server/test_signal.py"
    private double signalValue = 0.0; //!< response buffer
    private long timeOut = (long) (1000*MyFirData.sampletime);

    public int requestCounter = -1;
    private RequestQueue queue;

    public ServerIoT(String _ip, Context context) {
        ip = _ip + "/";
        queue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public double getTestSignal(int k) {
        String response = getServerResponse(k);
        signalValue = Double.parseDouble(response);
        return signalValue;
    }

    public void resetRequestCounter() {
        requestCounter = -1;
    }

    private String getServerResponse(int k)  {
        // Instantiate the RequestQueue with Volley
        String url = protocol + ip + script + "?k=" + Integer.toString(k);

        // Create future request
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, future, future);

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        try {
            return future.get(timeOut, TimeUnit.MILLISECONDS);

        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
            return Double.toString(signalValue);
        }
    }
}
