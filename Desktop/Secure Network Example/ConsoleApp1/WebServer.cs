using System;
using System.Diagnostics;
using System.Net.Http;
using System.Threading.Tasks;

namespace CommandLineClient
{
    public class WebServer
    {
        private readonly string protocol = "https://";
        private readonly string ip;

        public WebServer(string _ip)
        {
            ip = _ip;
        }

        /**
         * @brief Reasource.
         */
        public string Resource
        {
            get { return protocol + ip + "/file.json"; }
            set { }
        }


        /**
          * @brief HTTP GET request using HttpClient
          */
        public async Task<string> GetResource(string resource)
        {
            string responseText = null;

            try
            {
                using (HttpClient client = new HttpClient())
                {
                    responseText = await GetStringResource(client, resource);
                }
            }
            catch (Exception e)
            {
                Debug.WriteLine("NETWORK ERROR");
                Debug.WriteLine(e);
            }

            return responseText;
        }


        public async Task<string> GetStringResource(HttpClient client, string resource)
        {
            var type = GetType();
            var property = type.GetProperty(resource);
            var value = property.GetValue(this).ToString();
            return await client.GetStringAsync(value);
        }

    }
}
