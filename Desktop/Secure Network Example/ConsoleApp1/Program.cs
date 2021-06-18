using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace CommandLineClient
{
    class Program
    {
        static async Task Main(string[] args)
        {
            ServicePointManager.ServerCertificateValidationCallback += (sender, cert, chain, sslPolicyErrors) => true;

            var rpi = new WebServer("192.168.0.13");

            Console.WriteLine("Server response: {0}", await rpi.GetResource("Resource"));
            Console.ReadLine();
        }
    }
}
