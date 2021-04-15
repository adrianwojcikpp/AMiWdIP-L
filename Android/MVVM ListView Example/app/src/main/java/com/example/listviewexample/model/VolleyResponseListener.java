package com.example.listviewexample.model;

import org.json.JSONArray;

public interface VolleyResponseListener {
    void onError(String message);
    void onResponse(JSONArray response);
}
