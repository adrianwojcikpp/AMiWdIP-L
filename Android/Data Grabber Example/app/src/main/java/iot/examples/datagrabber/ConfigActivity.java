/**
 ******************************************************************************
 * @file    Data Grabber Example/ConfigActivity.java
 * @author  Adrian Wojcik
 * @version V1.0
 * @date    09-Apr-2020
 * @brief   Data grabber example: configuration activity with IP and sample time
 ******************************************************************************
 */

package iot.examples.datagrabber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class ConfigActivity extends AppCompatActivity {

    /* BEGIN config TextViews */
    EditText ipEditText;
    EditText sampleTimeEditText;
    /* END config TextViews */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        // get the Intent that started this Activity
        Intent intent = getIntent();

        // get the Bundle that stores the data of this Activity
        Bundle configBundle = intent.getExtras();

        if(configBundle != null) {
            ipEditText = findViewById(R.id.ipEditTextConfig);
            String ip = configBundle.getString(Common.CONFIG_IP_ADDRESS, Common.DEFAULT_IP_ADDRESS);
            ipEditText.setText(ip);

            sampleTimeEditText = findViewById(R.id.sampleTimeEditTextConfig);
            int st = configBundle.getInt(Common.CONFIG_SAMPLE_TIME, Common.DEFAULT_SAMPLE_TIME);
            sampleTimeEditText.setText(Integer.toString(st));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(Common.CONFIG_IP_ADDRESS, ipEditText.getText().toString());
        intent.putExtra(Common.CONFIG_SAMPLE_TIME, sampleTimeEditText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}