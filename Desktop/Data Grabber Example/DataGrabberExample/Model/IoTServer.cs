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
            = HashStringToByteArray("8F:CD:50:8B:D4:C7:EA:34:C1:A1:FC:63:FE:58:B3:AD:05:C7:CD:FD");

        // Public Key
        private static readonly byte[] validPublicKey
            = PublicKeyStringToByteArray("3082010A0282010100D90D582C4DF2E5E4A1" +
                                         "94AA76C1FC0B6FCBE477F84659E18344D736" +
                                         "9561EE74E59325CB254DFF7F0FF935CE6D1F" +
                                         "A18E9E5EC7A2323D19678DCB8105285E9A00" +
                                         "A6186349E3429C1C3777D3DBB4C18AAFB065" +
                                         "37DFC826BD7FEDDCD26A4A3709A4B8C7901C" +
                                         "F7B05C090BA6CD41D4F317955B0656C6841F" +
                                         "B6E33E362B5E39D176D28EFA33E5EDD25C85" +
                                         "2DDA969ECA0929C39AD14CF9215B2E910AA1" +
                                         "5CD747582B88B44563463B5AE5E0109EECD3" +
                                         "025F2477BCEBFD88B748995835ECFEF60741" +
                                         "9A9F3D8F3F05553279181B8C93434D403345" +
                                         "AA084147209799C75232D49FD9C58D66D47D" +
                                         "7DEB53D031E74955F1E20A94B46D9F12AF33" +
                                         "BDA9366B5043E914B9F2D6DAD50203010001");
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

        private static byte[] PublicKeyStringToByteArray(string key)
        {
            return Enumerable.Range(0, key.Length)
                     .Where(x => x % 2 == 0)
                     .Select(x => Convert.ToByte(key.Substring(x, 2), 16))
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

            X509Certificate2 cert2 = new X509Certificate2(cert);

            // WARNING! : ALL CERTICATES WILL BE VALID! SECURITY RISK! 
            //bool isValid = true;

            // Compare SHA-1
            //bool isValid = validHashBytes.SequenceEqual(cert2.GetCertHash());

            // Compare public keys
            bool isValid = validPublicKey.SequenceEqual(cert2.GetPublicKey());

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
