package iot.examples.leddisplay;

import org.json.JSONArray;
import org.json.JSONException;

public class LedDisplay {
    public final int SizeX = 8;
    public final int SizeY = 8;

    private int ActiveColorR; ///< Active color Red components
    private int ActiveColorG; ///< Active color Green components
    private int ActiveColorB; ///< Active color Blue components

    public final int OffColor;       ///< 'LED-is-off' color in Int ARGB format

    private final Integer[][][] Model = new Integer[SizeY][SizeX][3]; ///< LED display data model

    /**
     * @brief Default constructor
     */
    public LedDisplay(int offColor) {
        OffColor = offColor;
        ActiveColorR = (offColor >> 16) & 0xff;
        ActiveColorG = (offColor >> 8) & 0xff;;
        ActiveColorB = offColor & 0xff;;
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
            array.put(2, Model[x][y][0]);
            array.put(3, Model[x][y][1]);
            array.put(4, Model[x][y][2]);
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
        return !((Model[x][y][0]==null)||(Model[x][y][1]==null)||(Model[x][y][2]==null));
    }

    /**
     * @brief Active color getter
     * @return Active color in Int ARGB format
     */
    public int getActiveColor() {
        return  (getActiveColorA() & 0xff) << 24 | (ActiveColorR & 0xff) << 16 |
                     (ActiveColorG & 0xff) <<  8 | (ActiveColorB & 0xff);
    }

    /**
     * @brief Active color getter: A channel
     * @return Alpha channel value
     */
    private int getActiveColorA() {
        return (ActiveColorR+ActiveColorG+ActiveColorB)/3;
    }

    /**
     * @brief Active color setter: R channel
     * @param c Input color value
     */
    public void setActiveColorR(int c) {
        ActiveColorR = c;
    }

    /**
     * @brief Active color setter: G channel
     * @param c Input color value
     */
    public void setActiveColorG(int c) {
        ActiveColorG = c;
    }

    /**
     * @brief Active color setter: B channel
     * @param c Input color value
     */
    public void setActiveColorB(int c) {
        ActiveColorB = c;
    }

    /**
     * @brief Update display model with active color
     * @param x LED horizontal position in display
     * @param y LED vertical position in display
     */
    public void updateModel(int x, int y) {
        Model[x][y][0] = ActiveColorR;
        Model[x][y][1] = ActiveColorG;
        Model[x][y][2] = ActiveColorB;
    }

    /**
     * @brief LED display data model clear - fill with all components with Null
     */
    public void clearModel() {
        for(int i = 0; i < SizeX; i++) {
            for (int j = 0; j < SizeY; j++) {
                Model[i][j][0] = null;
                Model[i][j][1] = null;
                Model[i][j][2] = null;
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
        for(int i = 0; i < SizeX; i++) {
            for (int j = 0; j < SizeY; j++) {
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
