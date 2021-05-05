using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Media;

namespace ButtonMatrixExample.ViewModel
{
    public class MainViewModel : INotifyPropertyChanged
    {
        private Random rand = new Random();

        private readonly Action<String, Color> setColorHandler;
        public ButtonCommandWithParameter CommonButtonCommand { get; set; }

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
                    SetPreviewColor(_r, _g, _b);
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
                    SetPreviewColor(_r, _g, _b);
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
                    SetPreviewColor(_r, _g, _b);
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

        public MainViewModel(Action<String, Color> handler)
        {
            setColorHandler = handler;
            CommonButtonCommand = new ButtonCommandWithParameter(SetButtonColor);
        }

        public string LedIndexToTag(int i, int j)
        {
            return "BM" + i.ToString() + j.ToString();
        }

        private void SetPreviewColor(byte r, byte g, byte b)
        {
            byte a = (byte)((r + b + g) / 3);
            SelectedColor = new SolidColorBrush(Color.FromArgb(a,r,g,b));
        }

        private void SetButtonColor(string parameter)
        {
            setColorHandler(parameter, SelectedColor.Color);
        }

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

        #endregion
    }
}
