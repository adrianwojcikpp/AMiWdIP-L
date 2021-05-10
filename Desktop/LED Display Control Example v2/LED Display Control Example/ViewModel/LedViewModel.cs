/**
 ******************************************************************************
 * @file    LED Display Control Example/ViewModel/LedViewModel.cs
 * @author  Adrian Wojcik  adrian.wojcik@put.poznan.pl
 * @version V2.0
 * @date    10-May-2021
 * @brief   LED display controller: LED indicator ViewModel
 ******************************************************************************
 */

using System.Windows.Media;

namespace LedDisplayExample.ViewModel
{
    using Model;

    public class LedViewModel : BaseViewModel
    {
        private LedModel _model;  //!< LED model
        private SolidColorBrush _nullColor = new SolidColorBrush(Color.FromArgb(0, 0, 0, 0)); //!< Disabled LED color

        public SolidColorBrush ViewColor  //!< LED color property: presenting model as brush 
        {
            get => ModelToBrush();
            private set { }
        }

        /**
         * @brief Defualt constructor
         */
        public LedViewModel()
        {
            _model = new LedModel();
        }

        /**
         * @brief Parametric constructor
         * @param model LED model reference
         */
        public LedViewModel(LedModel model)
        {
            _model = model;
        }

        /**
         * @brief ViewColor property explicite setter
         * @param r Red color component
         * @param g Green color component
         * @param b Blue color component
         */
        public void SetViewColor(int r, int g, int b)
        {
            if (ColorChanged(r, g, b))
            {
                _model.R = (byte)r;
                _model.G = (byte)g;
                _model.B = (byte)b;
                OnPropertyChanged("ViewColor");
            }
        }

        /**
         * @brief Clear model and update ViewColor property
         */
        public void ClearViewColor()
        {
            if (_model.ColorNotNull())
            {
                _model.Clear();
                OnPropertyChanged("ViewColor");
            }
        }

        /**
         * @brief Converts LED model to Brush object
         * @return SolidColorBrush with color from LED model
         */
        private SolidColorBrush ModelToBrush()
        {
            if (_model.ColorNotNull())
            {
                var A = (byte)((_model.R + _model.G + _model.B) / 3);
                return new SolidColorBrush(Color.FromArgb(A, (byte)_model.R, (byte)_model.G, (byte)_model.B));
            }
            else
            {
                return _nullColor;
            }
        }

        /**
         * @brief Check if any color component have changed
         * @param r Red color component
         * @param g Green color component
         * @param b Blue color component
         * @return False is all components remains the same, True otherwise 
         */
        private bool ColorChanged(int r, int g, int b)
        {
            return (_model.R != (byte)r)  
                || (_model.G != (byte)g) 
                || (_model.B != (byte)b);
        }
    }
}
