/**
 ******************************************************************************
 * @file    LED Display Control Example/ViewModel/MainViewModel.cs
 * @author  Adrian Wojcik  adrian.wojcik@put.poznan.pl
 * @version V1.0
 * @date    07-May-2021
 * @brief   LED display controller: main view model
 ******************************************************************************
 */

using LedDisplayExample.Model;
using System;
using System.ComponentModel;
using System.Windows;
using System.Windows.Media;

namespace LedDisplayExample.ViewModel
{
    public class MainViewModel : INotifyPropertyChanged
    {
        #region Fields

        private IoTServer server;
        private Random rand = new Random();
        private readonly Action<String, Color> setColorHandler;
        
        public LedDisplay ledDisplay;

        #endregion Fileds

        #region Properties

        public ButtonCommandWithParameter CommonButtonCommand { get; set; }
        public ButtonCommand SendRequestCommand { get; set; }
        public ButtonCommand SendClearCommand { get; set; }

        private byte _r;
        public int R
        {
            get
            {
                return _r;
            }
            set
            {
                if(_r != (byte)value)
                {
                    _r = (byte)value;
                    ledDisplay.ActiveColorR = _r;
                    SelectedColor = new SolidColorBrush(ledDisplay.ActiveColor);
                    OnPropertyChanged("R");
                }
            }
        }

        private byte _g;
        public int G
        {
            get
            {
                return _g;
            }
            set
            {
                if (_g != (byte)value)
                {
                    _g = (byte)value;
                    ledDisplay.ActiveColorG = _g;
                    SelectedColor = new SolidColorBrush(ledDisplay.ActiveColor);
                    OnPropertyChanged("G");
                }
            }
        }

        private byte _b;
        public int B
        {
            get
            {
                return _b;
            }
            set
            {
                if (_b != (byte)value)
                {
                    _b = (byte)value;
                    ledDisplay.ActiveColorB = _b;
                    SelectedColor = new SolidColorBrush(ledDisplay.ActiveColor);
                    OnPropertyChanged("B");
                }
            }
        }

        private SolidColorBrush _selectedColor;
        public SolidColorBrush SelectedColor
        {
            get
            {
                return _selectedColor;
            }
            set
            {
                if(_selectedColor != value)
                {
                    _selectedColor = value;
                    OnPropertyChanged("SelectedColor");
                }
            }
        }

        #endregion Properties

        public MainViewModel(Action<String, Color> handler)
        {
            ledDisplay = new LedDisplay(0x00000000);
            SelectedColor = new SolidColorBrush(ledDisplay.ActiveColor);

            setColorHandler = handler;
            CommonButtonCommand = new ButtonCommandWithParameter(SetButtonColor);
            SendRequestCommand = new ButtonCommand(SendControlRequest);
            SendClearCommand = new ButtonCommand(SendClearRequest);

            server = new IoTServer("localhost");
        }

        #region Methods

        /**
         * @brief TODO
         * @param x 
         * @param y
         */
        public string LedIndexToTag(int i, int j)
        {
            return "LED" + i.ToString() + j.ToString();
        }

        /**
         * @brief TODO
         * @param name
         * @return
         */
        public (int, int) LedTagToIndex(string name)
        {
            return (int.Parse(name.Substring(3, 1)), int.Parse(name.Substring(4, 1)));
        }

        /**
         * @brief TODO
         * @param parameter
         */
        private void SetButtonColor(string parameter)
        {
            (int x, int y) = LedTagToIndex(parameter);
            ledDisplay.UpdateModel(x, y);
            setColorHandler(parameter, SelectedColor.Color);
        }

        /**
         * @brief TODO
         */
        private void ClearDisplay()
        {
            ledDisplay.ClearModel();
            for (int i = 0; i < ledDisplay.SizeX; i++)
                for (int j = 0; j < ledDisplay.SizeY; j++)
                    setColorHandler(LedIndexToTag(i, j), ledDisplay.OffColor);
        }

        /**
         * @brief TODO
         */
        private async void SendControlRequest()
        {
            await server.PostControlRequest(ledDisplay.getControlPostData());
        }

        /**
         * @brief TODO
         */
        private async void SendClearRequest()
        {
            ClearDisplay();
            await server.PostControlRequest(ledDisplay.getClearPostData());
        }

        #endregion Methods

        #region PropertyChanged

        public event PropertyChangedEventHandler PropertyChanged;

        /**
         * @brief Simple function to trigger event handler
         * @params propertyName Name of ViewModel property as string
         */
        protected void OnPropertyChanged(string propertyName)
        {
            PropertyChangedEventHandler handler = PropertyChanged;
            if (handler != null) handler(this, new PropertyChangedEventArgs(propertyName));
        }

        #endregion PropertyChanged
    }
}
