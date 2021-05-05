﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ListViewExample.ViewModel
{
    using Model;

    public class MeasurementViewModel : INotifyPropertyChanged
    {
        private string _name;
        public string Name
        {
            get
            {
                return _name;
            }
            set
            {
                if(_name != value)
                {
                    _name = value;
                    OnPropertyChanged("Name");
                }
            }
        }
        
        private double _data;
        public string Data
        {
            get
            {
                return _data.ToString("0.0####", CultureInfo.InvariantCulture);
            }
            set
            {
                if (Double.TryParse(value, NumberStyles.Any, CultureInfo.InvariantCulture, out double tmp) && _data != tmp)
                {
                    _data = tmp;
                    OnPropertyChanged("Data");
                }
            }
        }

        private string _unit;
        public string Unit
        {
            get
            {
                return _unit;
            }
            set
            {
                if (_unit != value)
                {
                    _unit = value;
                    OnPropertyChanged("Unit");
                }
            }
        }

        public MeasurementViewModel(MeasurementModel model)
        {
            UpdateWithModel(model);
        }

        public void UpdateWithModel(MeasurementModel model)
        {
            _name = model.Name;
            OnPropertyChanged("Name");
            _data = model.Data;
            OnPropertyChanged("Data");
            _unit = model.Unit;
            OnPropertyChanged("Unit");
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
