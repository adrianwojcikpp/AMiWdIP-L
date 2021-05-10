/**
 ******************************************************************************
 * @file    LED Display Control Example/Model/LedDisplayModel.cs
 * @author  Adrian Wojcik  adrian.wojcik@put.poznan.pl
 * @version V2.0
 * @date    10-May-2021
 * @brief   LED display controller: LED display data model - matrix of LEDs
 ******************************************************************************
 */

using Newtonsoft.Json.Linq;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text.Json;

namespace LedDisplayExample.Model
{
    public class LedDisplayModel
    {
        public readonly int SizeX = 8;  //!< Display horizontal size
        public readonly int SizeY = 8;  //!< Display vertical size
        private LedModel[,] _model;     //!< Display data model - matrix of LEDs

        /**
         * @brief Default constructor
         */ 
        public LedDisplayModel()
        {
            _model = new LedModel[SizeX, SizeY];
            for (int x = 0; x < SizeX; x++)
                for (int y = 0; y < SizeY; y++)
                    _model[x, y] = new LedModel();
        }

        /**
         * @brief Class indexer - access to model with '[]' operator
         * @param i First index of model container
         * @param j Second index of model container
         */
        public LedModel this[int i,int j]
        {
            get { return _model[i, j]; }
            set { _model[i, j] = value; }
        }

        /**
         * Conversion method: LED x-y position to position/color data in JSON format
         * @param x LED horizontal position in display
         * @param y LED vertical position in display
         * @return Position/color data in JSON format: [x,y,r,g,b] (x,y: 0-7; r,g,b: 0-255)
         */
        private JArray IndexToJsonArray(int x, int y)
        {
            JArray array = new JArray();
            try
            {
                array.Add(x);
                array.Add(y);
                array.Add(_model[x, y].R);
                array.Add(_model[x, y].G);
                array.Add(_model[x, y].B);
            }
            catch (JsonException e)
            {
                Trace.TraceError(e.Message);
            }
            return array;
        }

        /**
         * @brief Generate HTTP POST request parameters for LED display control via IoT server script
         * @return HTTP POST request parameters as Key-Value pairs
         */
        public List<KeyValuePair<string, string>> GetControlPostData()
        {
            var postData = new List<KeyValuePair<string, string>>();
            for (int i = 0; i < SizeX; i++)
            {
                for (int j = 0; j < SizeY; j++)
                {
                    if (_model[i,j].ColorNotNull())
                        postData.Add(
                            new KeyValuePair<string, string>(
                                "LED" + i.ToString() + j.ToString(),
                                IndexToJsonArray(i, j).ToString()
                                ));
                }
            }
            return postData;
        }

        /**
         * @brief Generate HTTP POST request parameters for clearing LED display via IoT server script
         * @return HTTP POST request parameters as Key-Value pairs
         */
        List<KeyValuePair<string, string>> clearData;
        public List<KeyValuePair<string, string>> GetClearPostData()
        {
            if (clearData == null)
            {
                clearData = new List<KeyValuePair<string, string>>();
                for (int i = 0; i < SizeX; i++)
                {
                    for (int j = 0; j < SizeY; j++)
                    {
                        clearData.Add(
                            new KeyValuePair<string, string>(
                                "LED" + i.ToString() + j.ToString(),
                                "[" + i.ToString() + "," + j.ToString() + ",0,0,0]"
                                ));
                    }
                }
            }
            return clearData;
        }
    }
}
