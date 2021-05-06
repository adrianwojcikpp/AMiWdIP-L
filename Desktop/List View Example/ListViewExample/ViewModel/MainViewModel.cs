using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;

namespace ListViewExample.ViewModel
{
    using Model;
    using Newtonsoft.Json.Linq;

    public class MainViewModel : INotifyPropertyChanged
    {
        public ObservableCollection<MeasurementViewModel> Measurements { get;  set; }
        public ButtonCommand Refresh { get; set; }

        private ServerIoTmock ServerMock = new ServerIoTmock();

        public MainViewModel()
        {
            // Create new collection for measurements data
            Measurements = new ObservableCollection<MeasurementViewModel>();

            // Bind button with action
            Refresh = new ButtonCommand(RefreshHandler);
        }

        void RefreshHandler()
        {
            // Read data from server in JSON array format
            // TODO: replace mock with network comunnication
            JArray measurementsJsonArray = ServerMock.getMeasurements();

            // Convert generic JSON array container to list of specific type
            var measurementsList = measurementsJsonArray.ToObject<List<MeasurementModel>>();

            // Add new elements to collection
            if(Measurements.Count < measurementsList.Count)
            {
                foreach (var m in measurementsList)
                    Measurements.Add(new MeasurementViewModel(m));
            }
            // Update existing elements in collection
            else
            {
                for (int i = 0; i < Measurements.Count; i++)
                    Measurements[i].UpdateWithModel(measurementsList[i]);
            }

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
