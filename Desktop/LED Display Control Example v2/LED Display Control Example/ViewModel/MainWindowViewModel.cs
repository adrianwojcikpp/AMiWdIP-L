/**
 ******************************************************************************
 * @file    LED Display Control Example/ViewModel/MainWindowViewModel.cs
 * @author  Adrian Wojcik  adrian.wojcik@put.poznan.pl
 * @version V2.0
 * @date    10-May-2021
 * @brief   LED display controller: main window ViewModel
 ******************************************************************************
 */

using System.Collections.Generic;
using System.Windows.Data;

namespace LedDisplayExample.ViewModel
{
    using Model;
    public class MainWindowViewModel : BaseViewModel
    {
        #region Fields
        private LedDisplayModel _disp;
        private IoTServer _server;
        #endregion Fields

        #region Properties
        public List<List<LedViewModel>> Leds { get; set; }          //!< LEDs ViewModels container 
        public List<List<ButtonCommand>> LedCommands { get; set; }  //!< LEDs commands container 
        public SliderViewModel Slider { get; set; }     //!< Color sliders ViewModel 
        public ButtonCommand SendCommand { get; set; }  //!< 'SEND' button command
        public ButtonCommand ClearCommand { get; set; } //!< 'CLEAR' button command
        public LedViewModel Preview { get; set; }       //!< Color preview ViewModel
        public int DisplaySizeX { get => _disp.SizeX; } //!< Display horizontal size
        public int DisplaySizeY { get => _disp.SizeY; } //!< Display vertical size
        #endregion Properties

        /**
         * @brief Defualt constructor
         */
        public MainWindowViewModel()
        {
            /* MODELS */
            _disp = new LedDisplayModel();
            _server = new IoTServer("localhost");

            /* VIEWMODELS */
            Preview = new LedViewModel();
            Slider = new SliderViewModel(Preview);

            /* VIEWMODELS CONTAINERS */
            Leds = new List<List<LedViewModel>>();
            for (int x = 0; x < DisplaySizeX; x++)
            {
                Leds.Add(new List<LedViewModel>());
                for (int y = 0; y < DisplaySizeY; y++)
                {
                    Leds[x].Add(new LedViewModel(_disp[x,y]));
                }
            }

            LedCommands = new List<List<ButtonCommand>>();
            for (int x = 0; x < DisplaySizeX; x++)
            {
                LedCommands.Add(new List<ButtonCommand>());
                for (int y = 0; y < DisplaySizeY; y++)
                {
                    var led = Leds[x][y];
                    LedCommands[x].Add(
                        new ButtonCommand(
                            () => led.SetViewColor(Slider.R, Slider.G, Slider.B) ));
                }
            }

            /* COMMANDS */
            SendCommand = new ButtonCommand(SendRequestHandler);
            ClearCommand = new ButtonCommand(ClearRequestHandler);
        }

        /**
         * @brief Conversion method: LED x-y position to Button command binding
         * @param x LED horizontal position in display
         * @param y LED vertical position in display
         * @return Button command binding
         */
        public Binding GetCommandBinding(int x, int y)
        {
            return new Binding("LedCommands[" + x.ToString() + "][" + y.ToString() + "]");
        }

        /**
         * @brief Conversion method: LED x-y position to LED indicator color binding
         * @param x LED horizontal position in display
         * @param y LED vertical position in display
         * @return LED indicator color binding
         */
        public Binding GetColordBinding(int x, int y)
        {
            return new Binding("Leds[" + x.ToString() + "][" + y.ToString() + "].ViewColor");
        }

        /**
         * @brief Send button Click event handling procedure
         */
        private async void SendRequestHandler()
        {
            await _server.PostControlRequest(_disp.GetControlPostData());
        }

        /**
          * @brief Clear button Click event handling procedure
          */
        private async void ClearRequestHandler()
        {
            // Clear display ViewModel
            for (int x = 0; x < DisplaySizeX; x++)
                for (int y = 0; y < DisplaySizeY; y++)
                    Leds[x][y].ClearViewColor();
            // Send request to clear device
            await _server.PostControlRequest(_disp.GetClearPostData());
        }
    }
}
