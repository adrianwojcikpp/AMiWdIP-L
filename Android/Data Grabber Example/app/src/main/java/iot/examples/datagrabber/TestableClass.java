/**
 ******************************************************************************
 * @file    Data Grabber Example/TestableClass.java
 * @author  Adrian Wojcik
 * @version V1.0
 * @date    09-Apr-2020
 * @brief   Data grabber example: testable class for JSON parsing
 ******************************************************************************
 */

package iot.examples.datagrabber;

import org.json.JSONException;
import org.json.JSONObject;

class TestableClass {

    /**
     * @brief Reading raw chart data from JSON response.
     * @param response IoT server JSON response as string
     * @retval new chart data
     */
    public double getRawDataFromResponse(String response) {
        JSONObject jObject;
        double x = Double.NaN;

        // Create generic JSON object form string
        try {
            jObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
            return x;
        }

        // Read chart data form JSON object
        try {
            x = jObject.getDouble("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return x;
    }

}
