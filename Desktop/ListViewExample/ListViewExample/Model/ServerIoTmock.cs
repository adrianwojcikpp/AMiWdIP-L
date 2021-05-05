using Newtonsoft.Json.Linq;
using System;
using System.Globalization;

namespace ListViewExample.Model
{
    public class ServerIoTmock
    {
        Random rand = new Random();

        public JArray getMeasurements()
        {
            string jsonText = "[";

            jsonText += "{\"Name\":\"Temperature\",\"Data\":" + (23.0 + rand.NextDouble()).ToString(CultureInfo.InvariantCulture) + ",\"Unit\":\"C\"},";
            jsonText += "{\"Name\":\"Pressure\",\"Data\":" + (1023.0 + rand.NextDouble()).ToString(CultureInfo.InvariantCulture) + ",\"Unit\":\"hPa\"},";
            jsonText += "{\"Name\":\"Humidity\",\"Data\":" + (43.0 + rand.NextDouble()).ToString(CultureInfo.InvariantCulture) + ",\"Unit\":\"%\"},";

            jsonText += "{\"Name\":\"Roll\",\"Data\":" + (180.0 + rand.NextDouble()).ToString(CultureInfo.InvariantCulture) + ",\"Unit\":\"Deg\"},";
            jsonText += "{\"Name\":\"Pitch\",\"Data\":" + (0.0 + rand.NextDouble()).ToString(CultureInfo.InvariantCulture) + ",\"Unit\":\"Deg\"},";
            jsonText += "{\"Name\":\"Yaw\",\"Data\":" + (270.0 + rand.NextDouble()).ToString(CultureInfo.InvariantCulture) + ",\"Unit\":\"Deg\"}";

            jsonText += "]";

            return JArray.Parse(jsonText);
        }
    }
}
