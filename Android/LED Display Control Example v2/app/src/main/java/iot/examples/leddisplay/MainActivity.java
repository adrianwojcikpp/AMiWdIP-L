/**
 ******************************************************************************
 * @file    LED Display Control Example/MainActivity.java
 * @author  Adrian Wojcik
 * @version V1.1
 * @date    04-May-2021
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private LedDisplay ledDisplay;  ///< LED display model

    /* BEGIN widgets */
    private View colorView;       ///< Color preview
    private EditText urlText;     ///< Input for IoT server script URL
    /* END widgets */

    /* BEGIN request */
    private String url = "http://10.0.2.2/led_display_put.php";  ///< Default IoT server script URL
    private RequestQueue queue; ///< HTTP requests queue
    /* END request */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* BEGIN LED table initialization: dynamic user interface handling */
        ledDisplay = new LedDisplay(getResources().getColor(R.color.ledIndBackground));
        ///< LED display matrix table
        TableLayout ledTable = (TableLayout) findViewById(R.id.led_table);
        for(int y = 0; y < ledDisplay.SizeY; y++) {
            //
            TableRow ledRow = new TableRow(this);
            ledRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            for (int x = 0; x < ledDisplay.SizeX; x++)
                ledRow.addView(addLedIndicatorToTableLayout(x, y));
            ledTable.addView(ledRow);
        }
        /* END LED table initialization */

        /* BEGIN widgets initialization */
        colorView = findViewById(R.id.colorView);

        urlText = findViewById(R.id.urlText);
        urlText.setText(url);

        /* BEGIN widgets */
        SeekBar redSeekBar = (SeekBar) findViewById(R.id.seekBarR);
        redSeekBar.setMax(255);
        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {/* Auto-generated method stub */ }
            public void onStopTrackingTouch(SeekBar seekBar) {
                ledDisplay.setActiveColorR(progressChangedValue);
                setLedViewColor(colorView, ledDisplay.getActiveColor());
            }
        });

        SeekBar greenSeekBar = (SeekBar) findViewById(R.id.seekBarG);
        greenSeekBar.setMax(255);
        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {/* Auto-generated method stub */ }
            public void onStopTrackingTouch(SeekBar seekBar) {
                ledDisplay.setActiveColorG(progressChangedValue);
                setLedViewColor(colorView, ledDisplay.getActiveColor());
            }
        });

        SeekBar blueSeekBar = (SeekBar) findViewById(R.id.seekBarB);
        blueSeekBar.setMax(255);
        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {/* Auto-generated method stub */ }
            public void onStopTrackingTouch(SeekBar seekBar) {
                ledDisplay.setActiveColorB(progressChangedValue);
                setLedViewColor(colorView, ledDisplay.getActiveColor());
            }
        });
        /* END widgets initialization */

        /* BEGIN 'Volley' request queue initialization */
        queue = Volley.newRequestQueue(this);
        /* END 'Volley' request queue initialization */
    }

    /**
     * @brief Conversion method: LED indicator Tag to LED x-y position
     * @param tag LED indicator View element Tag
     * @return Two-element vector with LED x-y position [0=x, 1=y]
     */
    private Vector<Integer> ledTagToIndex(String tag) {
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
    private String ledIndexToTag(int x, int y) {
        return "LED" + Integer.toString(x) + Integer.toString(y);
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

    /* LED indicator 'onClick' listener object */
    private final View.OnClickListener led_onClick = new View.OnClickListener() {
        /**
         * @brief LED indicator onClick event handling procedure
         * @param v LED indicator View element
         */
        @Override
        public void onClick(View v) {
            // Set active color as background
            setLedViewColor(v, ledDisplay.getActiveColor());
            // Find element x-y position
            String tag = (String)v.getTag();
            Vector<Integer> index = ledTagToIndex(tag);
            int x = (int)index.get(0);
            int y = (int)index.get(1);
            // Update LED display data model
            ledDisplay.updateModel(x,y);
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
        for(int i = 0; i < ledDisplay.SizeX; i++) {
            for (int j = 0; j < ledDisplay.SizeY; j++) {
                ledInd = tb.findViewWithTag(ledIndexToTag(i, j));
                ledInd.setBackgroundColor(ledDisplay.OffColor);
            }
        }

        // Clear LED display data model
        ledDisplay.clearModel();

        // Clear physical LED display
        jsonControlRequest(ledDisplay.getControlJsonArray());
    }

    /**
     * @brief Send button onClick event handling procedure
     * @param v Send button element
     */
    public void sendControlRequest(View v) {
        jsonControlRequest(ledDisplay.getControlJsonArray());
    }

    /**
     * @brief Send control request via Volley queue
     * @param data Send button element
     */
    public void jsonControlRequest(JSONArray data)
    {
        url = urlText.getText().toString();
        JsonArrayRequest putRequest = new JsonArrayRequest(Request.Method.PUT, url, data,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
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
