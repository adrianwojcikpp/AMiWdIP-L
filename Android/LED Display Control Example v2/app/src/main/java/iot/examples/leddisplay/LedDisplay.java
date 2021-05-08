/**
 ******************************************************************************
 * @file    LED Display Control Example/LedDisplay.java
 * @author  Adrian Wojcik
 * @version V1.1
 * @date    04-May-2021
 * @brief   LED display controller: LED display model
 ******************************************************************************
 */

package iot.examples.leddisplay;

import org.json.JSONArray;
import org.json.JSONException;

public class LedDisplay {
    public final int sizeX = 8;
    public final int sizeY = 8;

    private int activeColorR; ///< Active color Red components
    private int activeColorG; ///< Active color Green components
    private int activeColorB; ///< Active color Blue components

    public final int offColor;       ///< 'LED-is-off' color in Int ARGB format

    private final Integer[][][] model = new Integer[sizeY][sizeX][3]; ///< LED display data model

    /**
     * @brief Default constructor
     */
    public LedDisplay(int _offColor) {
        offColor = _offColor;
        activeColorR = (offColor >> 16) & 0xff;
        activeColorG = (offColor >> 8) & 0xff;;
        activeColorB = offColor & 0xff;;
        clearModel();
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
            array.put(2, model[x][y][0]);
            array.put(3, model[x][y][1]);
            array.put(4, model[x][y][2]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * @brief Null color check
     * @param x LED horizontal position in display
     * @param y LED vertical position in display
     * @return False if color is Null; True otherwise
     */
    private boolean colorNotNull(int x, int y) {
        return !((model[x][y][0]==null)||(model[x][y][1]==null)||(model[x][y][2]==null));
    }

    /**
     * @brief Active color getter
     * @return Active color in Int ARGB format
     */
    public int getActiveColor() {
        return  (getActiveColorA() & 0xff) << 24 | (activeColorR & 0xff) << 16 |
                     (activeColorG & 0xff) <<  8 | (activeColorB & 0xff);
    }

    /**
     * @brief Active color getter: A channel
     * @return Alpha channel value
     */
    private int getActiveColorA() {
        return (activeColorR + activeColorG + activeColorB)/3;
    }

    /**
     * @brief Active color setter: R channel
     * @param c Input color value
     */
    public void setActiveColorR(int c) {
        activeColorR = c;
    }

    /**
     * @brief Active color setter: G channel
     * @param c Input color value
     */
    public void setActiveColorG(int c) {
        activeColorG = c;
    }

    /**
     * @brief Active color setter: B channel
     * @param c Input color value
     */
    public void setActiveColorB(int c) {
        activeColorB = c;
    }

    /**
     * @brief Update display model with active color
     * @param x LED horizontal position in display
     * @param y LED vertical position in display
     */
    public void updateModel(int x, int y) {
        model[x][y][0] = activeColorR;
        model[x][y][1] = activeColorG;
        model[x][y][2] = activeColorB;
    }

    /**
     * @brief LED display data model clear - fill with all components with Null
     */
    public void clearModel() {
        for(int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                model[i][j][0] = null;
                model[i][j][1] = null;
                model[i][j][2] = null;
            }
        }
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
                if(colorNotNull(i,j)) {
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
