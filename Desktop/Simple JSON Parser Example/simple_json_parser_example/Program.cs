using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace simple_json_parser_example
{
    // Ex. 1A
    public class SimpleColorDataType_1
    {
        public double R { get; set; }
        public double G { get; set; }
        public double B { get; set; }
    }

    // Ex. 1B
    public class SimpleColorDataType_2 : List<double> { }

    // Ex. 1C
    public class SimpleColorDataType_3
    {
        public SimpleColorDataType_2 RGB { get; set; }
    }

    class Program
    {
        static void Main(string[] args)
        {
            Console.Write("Simple JSON parser example - C#/.Net App\n");

            /* A simple object containing information about the color in the RGB model consisting of three numeric values: R, G, and B. */
            string json_a = @"{'R':0,'G':100,'B':200}";

            dynamic dynamic_1 = JObject.Parse(json_a);
            JObject object_1 = JObject.Parse(json_a);
            SimpleColorDataType_1 color_1 = JsonConvert.DeserializeObject<SimpleColorDataType_1>(json_a);

            Console.Write("R = " + dynamic_1.R.ToString() + "\n");

            /* A simple array containing information about the color in the RGB model consisting of three numeric values. */
            string json_b = @"[0, 100, 200]";

            dynamic dynamic_2 = JArray.Parse(json_b);
            JArray object_2 = JArray.Parse(json_b);
            SimpleColorDataType_2 color_2 = JsonConvert.DeserializeObject<SimpleColorDataType_2>(json_b);

            Console.Write("G = " + dynamic_2[1].ToString() + "\n");

            /* A simple object containing information about the color in the RGB model consisting of one attribute-value pair RGB with an array value. */
            string json_c = @"{'RGB':[0, 100, 200]}";

            dynamic dynamic_3 = JObject.Parse(json_c);
            JObject object_3 = JObject.Parse(json_c);
            SimpleColorDataType_3 color_3 = JsonConvert.DeserializeObject<SimpleColorDataType_3>(json_c);

            Console.Write("B = " + dynamic_3.RGB[2].ToString() + "\n");

            Console.ReadKey();
        }
    }
}
