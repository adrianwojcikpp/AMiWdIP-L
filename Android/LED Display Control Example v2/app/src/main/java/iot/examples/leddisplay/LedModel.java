/**
 * *****************************************************************************
 * @file    LED Display Control Example/LedModel.java
 * @author  Adrian Wojcik  adrian.wojcik@put.poznan.pl
 * @version V2.0
 * @date    10-May-2021
 * @brief   LED display controller: LED data model - nullable RGB color format
 ******************************************************************************
 */

package iot.examples.leddisplay;

public class LedModel {

    public Integer R; //!< Red color component
    public Integer G; //!< Green color component
    public Integer B; //!< Blue color component

    /**
     * @brief Default constructor
     */
    public LedModel() {
        R = null;
        G = null;
        B = null;
    }

    /**
     * @brief Check if all color components are null
     * @return False if all color component are null, True otherwise
     */
    public boolean colorNotNull(){
        return (R != null) & (G != null) & (B != null);
    }

    /**
     * @brief Sets all color components to null
     */
    public void clear() {
        R = null;
        G = null;
        B = null;
    }

    /**
     * @brief Private R component getter
     * @return R component or 0 if R is null
     */
    private int getR(){
        if(R != null)
            return R;
        else
            return 0;
    }

    /**
     * @brief Private G component getter
     * @return G component or 0 if G is null
     */
    private int getG(){
        if(G != null)
            return G;
        else
            return 0;
    }

    /**
     * @brief Private B component getter
     * @return B component or 0 if B is null
     */
    private int getB(){
        if(B != null)
            return B;
        else
            return 0;
    }

    /**
     * @brief LED color getter
     * @return Color as integer in ARGB format
     */
    public int getColor() {
        int r = getR();
        int g = getG();
        int b = getB();
        int a = (r + g + b) / 3;
        return  ((a & 0xff) << 24) | ((r & 0xff) << 16) | ((g & 0xff) <<  8) | (b & 0xff);
    }

    /**
     * @brief LED color setter
     * @param mdl Input LED model
     */
    public void setColor(LedModel mdl) {
        R = mdl.getR();
        G = mdl.getG();
        B = mdl.getB();
    }
}
