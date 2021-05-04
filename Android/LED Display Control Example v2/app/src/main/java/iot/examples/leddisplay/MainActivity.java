/**
 ******************************************************************************
 * @file    LED Display Control Example/MainActivity.java
 * @author  Adrian Wojcik
 * @version V1.0
 * @date    09-Apr-2020
 * @brief   LED display controller: main activity with display GUI
 ******************************************************************************
 */

package iot.examples.leddisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    /* BEGIN widgets */
    private SeekBar redSeekBar, greenSeekBar, blueSeekBar;
    private View colorView;       ///< Color preview
    private EditText urlText;     ///< Input for IoT server script URL
    private TableLayout ledTable; ///< LED display matrix table
    /* END widgets */

    /* BEGIN LED display size */
    private final int ledSizeX = 8;
    private final int ledSizeY = 8;
    /* END LED display size */

    /* BEGIN colors */
    private int ledActiveColorA; ///< Active color Alpha components
    private int ledActiveColorR; ///< Active color Red components
    private int ledActiveColorG; ///< Active color Green components
    private int ledActiveColorB; ///< Active color Blue components

    private int ledActiveColor;  ///< Active color in Int ARGB format

    private int ledOffColor;       ///< 'LED-is-off' color in Int ARGB format
    private Vector<Integer> ledOffColorVec; ///< 'LED-is-off' color in Int ARGB format

    private Integer[][][] ledDisplayModel = new Integer[ledSizeY][ledSizeX][3]; ///< LED display data model
    /* BEGIN colors */

    /* BEGIN request */
    private String url = "http://10.0.2.2/led_display.php";  ///< Default IoT server script URL
    private RequestQueue queue; ///< HTTP requests queue
    private Map<String, String>  paramsClear = new HashMap<String, String>(); ///< HTTP POST data: clear display command
    /* END request */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* BEGIN Color data initialization */
        ledOffColor = getResources().getColor(R.color.ledIndBackground);
        ledOffColorVec = intToRgb(ledOffColor);

        ledActiveColor = ledOffColor;

        ledActiveColorR = 0x00;
        ledActiveColorG = 0x00;
        ledActiveColorB = 0x00;

        clearDisplayModel();
        /* END Color data initialization */

        /* BEGIN LED table initialization: dynamic user interface handling */
        ledTable = (TableLayout) findViewById(R.id.led_table);
        for(int y = 0; y < ledSizeY; y++) {
            //
            TableRow ledRow = new TableRow(this);
            ledRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            for (int x = 0; x < ledSizeX; x++)
                ledRow.addView(addLedIndicatorToTableLayout(x, y));
            ledTable.addView(ledRow);
        }
        /* END LED table initialization */

        /* BEGIN seek bars initialization */
        redSeekBar = (SeekBar)findViewById(R.id.seekBarR);
        redSeekBar.setMax(255);
        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {/* Auto-generated method stub */ }
            public void onStopTrackingTouch(SeekBar seekBar) {
                ledActiveColor = seekBarUpdate('R', progressChangedValue);
                setLedViewColor(colorView, ledActiveColor);
            }
        });

        greenSeekBar = (SeekBar)findViewById(R.id.seekBarG);
        greenSeekBar.setMax(255);
        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {/* Auto-generated method stub */ }
            public void onStopTrackingTouch(SeekBar seekBar) {
                ledActiveColor = seekBarUpdate('G', progressChangedValue);
                setLedViewColor(colorView, ledActiveColor);
            }
        });

        blueSeekBar = (SeekBar)findViewById(R.id.seekBarB);
        blueSeekBar.setMax(255);
        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {/* Auto-generated method stub */ }
            public void onStopTrackingTouch(SeekBar seekBar) {
                ledActiveColor = seekBarUpdate('B', progressChangedValue);
                setLedViewColor(colorView, ledActiveColor);
            }
        });

        colorView = findViewById(R.id.colorView);

        urlText = findViewById(R.id.urlText);
        urlText.setText(url);
        /* END seek bars initialization */

        /* BEGIN 'Volley' request queue initialization */
        queue = Volley.newRequestQueue(this);

        for(int i = 0; i < ledSizeX; i++) {
            for (int j = 0; j < ledSizeY; j++) {
                // "LEDij" : "[i,j,r,g,b]"
                String data ="["+Integer.toString(i)+","+Integer.toString(j)+",0,0,0]";
                paramsClear.put(ledIndexToTag(i, j), data);
            }
        }
        /* END 'Volley' request queue initialization */
    }

    /**
     * @brief Color conversion method: ARGB components to single ARGB integer value
     * @param _a Alpha component: 0-255 (0x00-0xFF)
     * @param _r Red component: 0-255 (0x00-0xFF)
     * @param _g Green component: 0-255 (0x00-0xFF)
     * @param _b Blue component: 0-255 (0x00-0xFF)
     * @return Color in ARGB format
     */
    public int argbToInt(int _a, int _r, int _g, int _b){
        return  (_a & 0xff) << 24 | (_r & 0xff) << 16 | (_g & 0xff) << 8 | (_b & 0xff);
    }

    /**
     * @brief Color conversion method: ARGB integer value to three-element Vector with RGB components
     * @param argb Color in ARGB format: 0x00000000-0xFFFFFFFF
     * @return Three-element Vector with RGB components [0=R, 1=G, 2=B]: 3x 0-255 (3x 0x00-0xFF)
     */
    public Vector<Integer> intToRgb(int argb) {
        int _r = (argb >> 16) & 0xff;
        int _g = (argb >> 8) & 0xff;
        int _b = argb & 0xff;
        Vector<Integer> rgb = new Vector<>(3);
        rgb.add(0,_r);
        rgb.add(1,_g);
        rgb.add(2,_b);
        return rgb;
    }

    /**
     * @brief Conversion method: LED indicator Tag to LED x-y position
     * @param tag LED indicator View element Tag
     * @return Two-element vector with LED x-y position [0=x, 1=y]
     */
    Vector<Integer> ledTagToIndex(String tag) {
        // Tag: 'LEDxy"
        Vector<Integer> vec = new Vector<>(2);
        vec.add(0, Character.getNumericValue(tag.charAt(3)));
        vec.add(1, Character.getNumericValue(tag.charAt(4)));
        return vec;
    }

    /**
     * @brief Conversion method: LED x-y position to LED indicator Tag
     * @param x LED horizontal position in display
     * @param y LED vertical position in display
     * @return LED indicator View element Tag
     */
    String ledIndexToTag(int x, int y) {
        return "LED" + Integer.toString(x) + Integer.toString(y);
    }

    /**
     * Conversion method: LED x-y position to position/color data in JSON format
     * @param x LED horizontal position in display
     * @param y LED vertical position in display
     * @return Position/color data in JSON format: [x,y,r,g,b] (x,y: 0-7; r,g,b: 0-255)
     */
    String ledIndexToJsonData(int x, int y) {
        String _x = Integer.toString(x);
        String _y = Integer.toString(y);
        String _r = Integer.toString(ledDisplayModel[x][y][0]);
        String _g = Integer.toString(ledDisplayModel[x][y][1]);
        String _b = Integer.toString(ledDisplayModel[x][y][2]);
        return "["+_x+","+_y+","+_r+","+_g+","+_b+"]";
    }

    /**
     * @brief Null color check
     * @param x LED horizontal position in display
     * @param y LED vertical position in display
     * @return False if color is Null; True otherwise
     */
    boolean ledColorNotNull(int x, int y) {
        return !((ledDisplayModel[x][y][0]==null)||(ledDisplayModel[x][y][1]==null)||(ledDisplayModel[x][y][2]==null));
    }

    /**
     * @brief LED display data model clear - fill with all components with Null
     */
    public void clearDisplayModel() {
        for(int i = 0; i < ledSizeX; i++) {
            for (int j = 0; j < ledSizeY; j++) {
                ledDisplayModel[i][j][0] = null;
                ledDisplayModel[i][j][1] = null;
                ledDisplayModel[i][j][2] = null;
            }
        }
    }

    /**
     * @brief Common method for RGB seek bars update
     * @param color Component selector: 'R' / 'G' / 'B'
     * @param value Component byte value: 0-255 (0x00-0xFF)
     * @return Color in ARGB format
     */
    int seekBarUpdate(char color, int value) {
        switch(color) {
            case 'R': ledActiveColorR = value; break;
            case 'G': ledActiveColorG = value; break;
            case 'B': ledActiveColorB = value; break;
            default: /* Do nothing */ break;
        }
        ledActiveColorA = (ledActiveColorR+ledActiveColorG+ledActiveColorB)/3;
        return argbToInt(ledActiveColorA,  ledActiveColorR, ledActiveColorG, ledActiveColorB);
    }

    /* LED indicator 'onClick' listener object */
    private View.OnClickListener led_onClick = new View.OnClickListener() {
        /**
         * @brief LED indicator onClick event handling procedure
         * @param v LED indicator View element
         */
        @Override
        public void onClick(View v) {
            // Set active color as background
            setLedViewColor(v, ledActiveColor);
            // Find element x-y position
            String tag = (String)v.getTag();
            Vector<Integer> index = ledTagToIndex(tag);
            int x = (int)index.get(0);
            int y = (int)index.get(1);
            // Update LED display data model
            ledDisplayModel[x][y][0] = ledActiveColorR;
            ledDisplayModel[x][y][1] = ledActiveColorG;
            ledDisplayModel[x][y][2] = ledActiveColorB;
        }
    };

    /**
     * @brief Clear button onClick event handling procedure
     * @param v Clear button element
     */
    public void clearAllLed(View v) {
        // Clear LED display GUI
        TableLayout tb = (TableLayout)findViewById(R.id.led_table);
        View ledInd;
        for(int i = 0; i < ledSizeX; i++) {
            for (int j = 0; j < ledSizeY; j++) {
                ledInd = tb.findViewWithTag(ledIndexToTag(i, j));
                ledInd.setBackgroundColor(ledOffColor);
            }
        }

        // Clear LED display data model
        clearDisplayModel();

        // Clear physical LED display
        sendClearRequest();
    }

    /**
     * @brief Generate HTTP POST request parameters for LED display control via IoT server script
     * @return HTTP POST request parameters as hash map (String keys, String values)
     */
    public Map<String, String>  getDisplayControlParams() {
        String led;
        String position_color_data;
        Map<String, String>  params = new HashMap<String, String>();
        for(int i = 0; i < ledSizeX; i++) {
            for (int j = 0; j < ledSizeY; j++) {
                if(ledColorNotNull(i,j)) {
                    led = ledIndexToTag(i, j);
                    position_color_data = ledIndexToJsonData(i, j);
                    params.put(led, position_color_data);
                }
            }
        }
        return params;
    }

    /**
     * @brief Send button onClick event handling procedure: send control request via Volley queue
     * @param v Send button element
     */
    public void sendControlRequest(View v)
    {
        url = urlText.getText().toString();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equals("ACK"))
                            Log.d("Response", "\n" + response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String msg = error.getMessage();
                        if(msg != null)
                            Log.d("Error.Response", msg);
                        else {
                            // TODO: error type specific code
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return getDisplayControlParams();
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(postRequest);
    }

    /**
     * @brief Send clear request via Volley queue
     */
    void sendClearRequest()
    {
        url = urlText.getText().toString();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        // TODO: check if ACK is valid
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String msg = error.getMessage();
                        if(msg != null)
                            Log.d("Error.Response", msg);
                        else {
                            // TODO: error type specific code
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return paramsClear;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(postRequest);
    }

    /**
     * @brief Dynamic LED indicator generator
     * @param x LED vertical position [0-ledSizeX]
     * @param y LED horizontal position [0-ledSizeY]
     * @return LED indicator view in form of RelativeLayout
     */
    private RelativeLayout addLedIndicatorToTableLayout(int x, int y)
    {
        // Border: RelativeView with border background drawable element
        RelativeLayout border = new RelativeLayout(this);
        // Layout parameters
        int ledIndWidth = (int)getResources().getDimension(R.dimen.ledIndWidth);
        int ledIndHeight = (int)getResources().getDimension(R.dimen.ledIndHeight);
        TableRow.LayoutParams border_params =
                new TableRow.LayoutParams(ledIndWidth, ledIndHeight);
        int borderThickness_px = (int)getResources().getDimension(R.dimen.borderThickness);
        border_params.setMargins(borderThickness_px, borderThickness_px,
                borderThickness_px, borderThickness_px);
        border_params.gravity = Gravity.CENTER;
        border_params.weight = 1;
        border.setLayoutParams(border_params);
        // Background drawable element
        border.setBackground(getResources().getDrawable(R.drawable.led_border));

        // LED indicator: View with LED background color
        View led = new View(this);
        // Layout parameters
        RelativeLayout.LayoutParams led_params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT );
        int ledIndMarginTopBottom_px = (int)getResources().getDimension(R.dimen.ledIndMarginTopBottom);
        int ledIndMarginLeftRight_px = (int)getResources().getDimension(R.dimen.ledIndMarginLeftRight);
        led_params.setMargins(ledIndMarginLeftRight_px, ledIndMarginTopBottom_px,
                ledIndMarginLeftRight_px, ledIndMarginTopBottom_px);
        led.setLayoutParams(led_params);
        // Background color
        //led.setBackgroundColor(getResources().getColor(R.color.ledIndBackground));
        led.setBackground(getResources().getDrawable(R.drawable.led_view));

        // OnClick method listener
        led.setOnClickListener(led_onClick);
        // Element tag
        led.setTag(ledIndexToTag(x, y));

        // Add 'led' View to border RelativeLayout
        border.addView(led);

        return border;
    }

    /**
     * @brief Setting View color for typical color representations
     * @param v View with drawable background
     * @param color Color in ARGB format: 0x00000000-0xFFFFFFFF
     */
    private void setLedViewColor(View v, int color){
        Drawable backgroundColor = v.getBackground();
        if (backgroundColor instanceof ShapeDrawable) {
            ((ShapeDrawable)backgroundColor).getPaint().setColor(color);
        } else if (backgroundColor instanceof GradientDrawable) {
            ((GradientDrawable)backgroundColor).setColor(color);
        } else if (backgroundColor instanceof ColorDrawable) {
            ((ColorDrawable)backgroundColor).setColor(color);
        }
    }
}
