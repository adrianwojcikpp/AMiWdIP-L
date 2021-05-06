namespace DataGrabberExample.Model
{
    public class ConfigParams
    {
        static readonly string ipAddressDefault = "localhost";
        public string IpAddress;
        static readonly int sampleTimeDefault = 500;
        public int SampleTime;
        public readonly int MaxSampleNumber = 100;
        public double XAxisMax
        {
            get
            {
                return MaxSampleNumber * SampleTime / 1000.0;
            }
            private set { }
        }

        public ConfigParams()
        {
            IpAddress = ipAddressDefault;
            SampleTime = sampleTimeDefault;
        }

        public ConfigParams(string ip, int st)
        {
            IpAddress = ip;
            SampleTime = st;
        }
    }
}
