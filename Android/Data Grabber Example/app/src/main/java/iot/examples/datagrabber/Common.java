/**
 ******************************************************************************
 * @file    Data Grabber Example/Common.java
 * @author  Adrian Wojcik
 * @version V1.0
 * @date    09-Apr-2020
 * @brief   Data grabber example: common app information
 ******************************************************************************
 */

package iot.examples.datagrabber;

final class Common {
    // activities request codes
    public final static int REQUEST_CODE_CONFIG = 1;

    // configuration info: names and default values
    public final static String CONFIG_IP_ADDRESS = "ipAddress";
    public final static String DEFAULT_IP_ADDRESS = "192.168.56.5"; //"192.168.0.12";

    public final static String CONFIG_SAMPLE_TIME = "sampleTime";
    public final static int DEFAULT_SAMPLE_TIME = 500;

    // error codes
    public final static int ERROR_TIME_STAMP = -1;
    public final static int ERROR_NAN_DATA = -2;
    public final static int ERROR_RESPONSE = -3;

    // IoT server data
    public final static String FILE_NAME = "resource.php?id=0001";
}
