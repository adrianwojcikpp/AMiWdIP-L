using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Threading.Tasks;

namespace DataGrabberExample.Model
{
    public class IoTServer
    {
        private readonly string protocol;
        private readonly string ip;

        // SHA-1 
        private static readonly byte[] validHashBytes 
            = HashStringToByteArray("A8:81:16:55:6D:3B:54:CE:11:7B:62:83:AE:52:15:20:95:71:43:4E");

        public IoTServer(string _protocol, string _ip)
        {
            ip = _ip;
            if(_protocol.ToLower() == "https")
            {
                protocol = "https://";
                ServicePointManager.ServerCertificateValidationCallback += ValidateServerCertficate;
            }
            else
            {
                protocol = "http://";
            }
        }

        private static byte[] HashStringToByteArray(string hash)
        {
            var hex = hash.Replace(":", "");
            return Enumerable.Range(0, hex.Length)
                     .Where(x => x % 2 == 0)
                     .Select(x => Convert.ToByte(hex.Substring(x, 2), 16))
                     .ToArray();
        }

        /**
          * @brief Validates the SSL server certificate.
          * @param[in] sender : An object that contains state information for this validation
          * @param[in] cert : The certificate used to authenticate the remote party.
          * @param[in] chain : The chain of certificate authorities associated with the remote certificate
          * @param[in] sslPolicyErrors : One or more errors associated with the remote certificate
          * @return Returns a boolean value that determines whether the specified
          *         certificate is accepted for authentication; true to accept or false to
          *          reject.
          *          
          */
        private static bool ValidateServerCertficate(object sender, 
            X509Certificate cert, X509Chain chain, SslPolicyErrors sslPolicyErrors)
        {
            // If no SSL errors
            if (sslPolicyErrors == SslPolicyErrors.None)
                return true;

            // WARNING! : ALL CERTICATES WILL BE VALID! SECURITY RISK! 
            //bool isValid = true;

            // Compare SHA-1
            bool isValid = validHashBytes.SequenceEqual(cert.GetCertHash());

            return isValid;
        }

        /**
          * @brief obtaining the address of the data file from IoT server IP.
          */
        private string GetFileUrl()
        {
            return protocol + ip + "/file.json";
        }

        /**
         * @brief obtaining the address of the PHP script from IoT server IP.
         */
        private string GetScriptUrl()
        {
            return protocol + ip + "/server/resource.php";
        }

        /**
          * @brief HTTP GET request using HttpClient
          */
        public async Task<string> GETwithClient()
        {
            string responseText = null;
            ServicePointManager.Expect100Continue = true;
            ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
            try
            {
                using (HttpClient client = new HttpClient())
                {
                    responseText = await client.GetStringAsync(GetFileUrl());
                }
            }
            catch (Exception e)
            {
                Debug.WriteLine("NETWORK ERROR");
                Debug.WriteLine(e);
            }

            return responseText;
        }

        /**
          * @brief HTTP POST request using HttpClient
         */
        public async Task<string> POSTwithClient()
        {
            string responseText = null;

            try
            {
                using (HttpClient client = new HttpClient())
                {
                    // POST request data
                    var requestDataCollection = new List<KeyValuePair<string, string>>();
                    requestDataCollection.Add(new KeyValuePair<string, string>("filename", "chartdata"));
                    var requestData = new FormUrlEncodedContent(requestDataCollection);
                    // Sent POST request
                    var result = await client.PostAsync(GetScriptUrl(), requestData);
                    // Read response content
                    responseText = await result.Content.ReadAsStringAsync();
                }
            }
            catch (Exception e)
            {
                Debug.WriteLine("NETWORK ERROR");
                Debug.WriteLine(e);
            }

            return responseText;
        }

        /**
          * @brief HTTP GET request using HttpWebRequest
          */
        public async Task<string> GETwithRequest()
        {
            string responseText = null;

            try
            {
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(GetFileUrl());

                request.Method = "GET";

                using (HttpWebResponse response = (HttpWebResponse)await request.GetResponseAsync())
                using (Stream stream = response.GetResponseStream())
                using (StreamReader reader = new StreamReader(stream))
                {
                    responseText = await reader.ReadToEndAsync();
                }
            }
            catch (Exception e)
            {
                Debug.WriteLine("NETWORK ERROR");
                Debug.WriteLine(e);
            }

            return responseText;
        }

        /**
          * @brief HTTP POST request using HttpWebRequest
          */
        public async Task<string> POSTwithRequest()
        {
            string responseText = null;

            try
            {
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(GetScriptUrl());

                // POST Request data 
                var requestData = "filename=chartdata";
                byte[] byteArray = Encoding.UTF8.GetBytes(requestData);
                // POST Request configuration
                request.Method = "POST";
                request.ContentType = "application/x-www-form-urlencoded";
                request.ContentLength = byteArray.Length;
                // Wrire data to request stream
                Stream dataStream = request.GetRequestStream();
                dataStream.Write(byteArray, 0, byteArray.Length);
                dataStream.Close();

                using (HttpWebResponse response = (HttpWebResponse)await request.GetResponseAsync())
                using (Stream stream = response.GetResponseStream())
                using (StreamReader reader = new StreamReader(stream))
                {
                    responseText = await reader.ReadToEndAsync();
                }
            }
            catch (Exception e)
            {
                Debug.WriteLine("NETWORK ERROR");
                Debug.WriteLine(e);
            }

            return responseText;
        }
    }
}
