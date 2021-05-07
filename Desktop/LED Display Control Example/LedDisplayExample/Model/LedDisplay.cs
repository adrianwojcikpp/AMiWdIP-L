﻿/**
 ******************************************************************************
 * @file    LED Display Control Example/Model/LedDisplay.cs
 * @author  Adrian Wojcik  adrian.wojcik@put.poznan.pl
 * @version V1.0
 * @date    07-May-2021
 * @brief   LED display controller: LED display model
 ******************************************************************************
 */

using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Windows.Media;

namespace LedDisplayExample.Model
{
    public class LedDisplay
    {
        public readonly int SizeX = 8;
        public readonly int SizeY = 8;

        public byte ActiveColorA    //!< Active color Alpha components
        {
            get => (byte)((_activeColorR + _activeColorG + _activeColorB) / 3);   
        }

        private byte _activeColorR; 
        public byte ActiveColorR    //!< Active color Red components
        {
            set => _activeColorR = value;
        }

        private byte _activeColorG;
        public byte ActiveColorG    //!< Active color Green components
        {
            set => _activeColorG = value;
        }

        private byte _activeColorB;
        public byte ActiveColorB    //!< Active color Blue components
        {
            set => _activeColorB = value;
        }

        public Color ActiveColor    //!< Active color in ARG format
        {
            get => Color.FromArgb(ActiveColorA, _activeColorR, _activeColorG, _activeColorB);
        }

        public readonly Color OffColor;   //!< 'LED-is-off' color in Int ARGB format

        private UInt16?[,,] model;

        /**
         * @brief Default constructor
         */
        public LedDisplay(int offColor)
        {
            model = new UInt16?[SizeX, SizeY, 3];
            OffColor = Color.FromArgb((byte)(offColor >> 24), (byte)(offColor >> 16), (byte)(offColor >> 8), (byte)offColor);
            _activeColorR = OffColor.R;
            _activeColorG = OffColor.G;
            _activeColorB = OffColor.B;
            ClearModel();
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
                array.Add(model[x, y, 0]);
                array.Add(model[x, y, 1]);
                array.Add(model[x, y, 2]);
            }
            catch (JsonException e)
            {
                Trace.TraceError(e.Message);
            }
            return array;
        }

        /**
         * @brief Null color check
         * @param x LED horizontal position in display
         * @param y LED vertical position in display
         * @return False if color is Null; True otherwise
         */
        private bool ColorNotNull(int x, int y)
        {
            return !((model[x,y,0] == null) || (model[x,y,1] == null) || (model[x,y,2] == null));
        }

        /**
         * @brief Update display model with active color
         * @param x LED horizontal position in display
         * @param y LED vertical position in display
         */
        public void UpdateModel(int x, int y)
        {
            model[x, y, 0] = _activeColorR;
            model[x, y, 1] = _activeColorG;
            model[x, y, 2] = _activeColorB;
        }

        /**
         * @brief LED display data model clear - fill with all components with Null
         */
        public void ClearModel()
        {
            for (int i = 0; i < SizeX; i++)
            {
                for (int j = 0; j < SizeY; j++)
                {
                    model[i, j, 0] = null;
                    model[i, j, 1] = null;
                    model[i, j, 2] = null;
                }
            }
        }

        /**
         * @brief Generate HTTP POST request parameters for LED display control via IoT server script
         * @return HTTP POST request parameters as Key-Value pairs
         */
        public List<KeyValuePair<string, string>> getControlPostData()
        {
            var postData = new List<KeyValuePair<string, string>>();
            for (int i = 0; i < SizeX; i++)
            {
                for (int j = 0; j < SizeY; j++)
                {
                    if (ColorNotNull(i, j))
                        postData.Add(
                            new KeyValuePair<string, string>(
                                "LED" + i.ToString() + j.ToString(),
                                IndexToJsonArray(i,j).ToString()
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
        public List<KeyValuePair<string, string>> getClearPostData()
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
                                "["+i.ToString()+ ","+ j.ToString()+",0,0,0]"
                                ));
                    }
                }
            }
            return clearData;
        }
    }
}
