using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace filter_example.Model
{
    public class ServerIoT
    {
        private string protocol = "http://";
        private string ip;
        private string script = "server/test_signal.php"; // cgi-bin/server/test_signal.py
        private double signalValue = 0.0;

        public ServerIoT(string _ip)
        {
            ip = _ip + "/";
        }

        public async Task<double> getTestSignal(int k)
        {
            string response;
            string url = protocol + ip + script + "?k=" + k.ToString();
            
            using (HttpClient client = new HttpClient() { Timeout = TimeSpan.FromSeconds(MyFirData.sampletime) })
            {
                response = await client.GetStringAsync(url);
            }

            Double.TryParse(response, NumberStyles.Any, CultureInfo.InvariantCulture, out signalValue);

            return signalValue;
        }
    }
}
