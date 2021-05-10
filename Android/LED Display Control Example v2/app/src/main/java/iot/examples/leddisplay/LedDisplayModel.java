/**
 ******************************************************************************
 * @file    LED Display Control Example/Model/LedDisplayModel.java
 * @author  Adrian Wojcik  adrian.wojcik@put.poznan.pl
 * @version V2.0
 * @date    10-May-2021
 * @brief   LED display controller: LED display data model - matrix of LEDs
 ******************************************************************************
 */

package iot.examples.leddisplay;

import org.json.JSONArray;
import org.json.JSONException;

public class LedDisplayModel {
    public final int sizeX = 8;         //!< Display horizontal size
    public final int sizeY = 8;         //!< Display vertical size
    private final LedModel[][] model;   //!< Display data model - matrix of LEDs

    /**
     * @brief Default constructor
     */
    public LedDisplayModel(){
        model = new LedModel[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++)
            for (int y = 0; y < sizeY; y++)
                model[x][y] = new LedModel();
    }

    /**
     * @brief Model element setter
     * @param i First index of model container
     * @param j Second index of model container
     * @param mdl Input LED model
     */
    public void setLedModel(int i, int j, LedModel mdl){
        model[i][j].setColor(mdl);
    }

    /**
     * @brief LED display data model clear - fill with all components with Null
     */
    public void clearModel() {
        for(int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                model[i][j].clear();
            }
        }
    }

    /**
     * Conversion method: LED x-y position to position/color data in JSON format
     * @param x LED horizontal position in display
     * @param y LED vertical position in display
     * @return Position/color data in JSON format: [x,y,r,g,b] (x,y: 0-7; r,g,b: 0-255)
     */
    private JSONArray indexToJsonArray(int x, int y) {
        JSONArray array = new JSONArray();
        try {
            array.put(0, x);
            array.put(1, y);
            array.put(2, model[x][y].R);
            array.put(3, model[x][y].G);
            array.put(4, model[x][y].B);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * @brief Generate HTTP PUT request parameters for LED display control via IoT server script
     * @return HTTP PUT request parameters as JSON array
     */
    public JSONArray getControlJsonArray() {
        int led_n = 0;
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if(model[i][j].colorNotNull()) {
                    try {
                        jsonArray.put(led_n, indexToJsonArray(i, j));
                        led_n++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jsonArray;
    }
}
